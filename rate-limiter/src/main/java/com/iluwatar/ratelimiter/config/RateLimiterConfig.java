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

  @Bean
  public RateLimiter rateLimiter() {
    String algorithm = environment.getProperty("ratelimiter.algorithm", "FIXED_WINDOW");
    switch (algorithm) {
      case "SLIDING_WINDOW":
        return new SlidingWindowRateLimiter();
      case "TOKEN_BUCKET":
        return new TokenBucketRateLimiter();
      case "LEAKY_BUCKET":
        return new LeakyBucketRateLimiter();
      case "FIXED_WINDOW":
      default:
        return new FixedWindowRateLimiter();
    }
  }

  @Bean
  public ThrottlingStrategy throttlingStrategy() {
    String strategy = environment.getProperty("ratelimiter.throttlingStrategy", "DELAY");
    switch (strategy) {
      case "REJECT":
        return new RejectThrottlingStrategy();
      case "EXPONENTIAL_BACKOFF":
        return new ExponentialBackoffStrategy();
      case "DELAY":
      default:
        return new DelayThrottlingStrategy();
    }
  }
}
