package com.iluwatar.ratelimiter.config;

import com.iluwatar.ratelimiter.RateLimiter;
import com.iluwatar.ratelimiter.ThrottlingStrategy;
import com.iluwatar.ratelimiter.algorithms.FixedWindowRateLimiter;
import com.iluwatar.ratelimiter.algorithms.SlidingWindowRateLimiter;
import com.iluwatar.ratelimiter.algorithms.TokenBucketRateLimiter;
import com.iluwatar.ratelimiter.algorithms.LeakyBucketRateLimiter;
import com.iluwatar.ratelimiter.strategies.DelayThrottlingStrategy;
import com.iluwatar.ratelimiter.strategies.RejectThrottlingStrategy;
import com.iluwatar.ratelimiter.strategies.ExponentialBackoffStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RateLimiterConfig {

  private final Environment environment;

  public RateLimiterConfig(Environment environment) {
    this.environment = environment;
  }

  public Environment getEnvironment() {
    return environment;
  }

  @Bean
  public RateLimiter rateLimiter() {
    String algorithm = environment.getProperty("ratelimiter.algorithm", "FIXED_WINDOW");
    int maxRequests = Integer.parseInt(environment.getProperty("ratelimiter.maxRequests", "100"));
    long windowSizeMillis = Long.parseLong(environment.getProperty("ratelimiter.windowSizeMillis", "10000"));

    switch (algorithm) {
      case "SLIDING_WINDOW":
        return new SlidingWindowRateLimiter(maxRequests, windowSizeMillis);
      case "TOKEN_BUCKET":
        return new TokenBucketRateLimiter(maxRequests, windowSizeMillis);
      case "LEAKY_BUCKET":
        return new LeakyBucketRateLimiter(maxRequests, windowSizeMillis);
      case "FIXED_WINDOW":
      default:
        return new FixedWindowRateLimiter(maxRequests, windowSizeMillis);
    }
  }

  @Bean
  public ThrottlingStrategy throttlingStrategy() {
    String strategy = environment.getProperty("ratelimiter.throttlingStrategy", "DELAY");
    int maxRetries = Integer.parseInt(environment.getProperty("ratelimiter.maxRetries", "3"));
    long delayMillis = Long.parseLong(environment.getProperty("ratelimiter.delayMillis", "1000"));
    double backoffFactor = Double.parseDouble(environment.getProperty("ratelimiter.backoffFactor", "2.0"));

    switch (strategy) {
      case "REJECT":
        return new RejectThrottlingStrategy();
      case "EXPONENTIAL_BACKOFF":
        return new ExponentialBackoffStrategy(maxRetries, delayMillis, backoffFactor);
      case "DELAY":
      default:
        return new DelayThrottlingStrategy(delayMillis);
    }
  }

  /**
   * Fetches the rate limiting algorithm type for a given endpoint.
   * If no specific algorithm is configured, it defaults to FIXED_WINDOW.
   *
   * @param endpoint the endpoint for which the algorithm type is to be fetched
   * @return the AlgorithmType for the specified endpoint
   */
  public AlgorithmType getAlgorithmByEndpoint(String endpoint) {
    String algorithm = environment.getProperty("rate.limiter.algorithm." + endpoint, "FIXED_WINDOW");
    return AlgorithmType.valueOf(algorithm);
  }

  /**
   * Fetches the throttling strategy for a given endpoint and client type.
   * If no specific strategy is configured, it defaults to DELAY.
   *
   * @param endpoint the endpoint for which the strategy is to be fetched
   * @param clientType the client type for which the strategy is to be fetched
   * @return the ThrottlingStrategyType for the specified endpoint and client type
   */
  public ThrottlingStrategyType getThrottlingStrategyByEndpointAndClientType(String endpoint, String clientType) {
    String strategy = environment.getProperty("rate.limiter.throttling." + endpoint + "." + clientType, "DELAY");
    return ThrottlingStrategyType.valueOf(strategy);
  }
}