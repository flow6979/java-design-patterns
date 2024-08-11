package com.iluwatar.ratelimiter.factory;

import com.iluwatar.ratelimiter.ThrottlingStrategy;
import com.iluwatar.ratelimiter.strategies.DelayThrottlingStrategy;
import com.iluwatar.ratelimiter.strategies.RejectThrottlingStrategy;
import com.iluwatar.ratelimiter.strategies.ExponentialBackoffStrategy;
import com.iluwatar.ratelimiter.config.RateLimiterConfig;

public class ThrottlingStrategyFactory {

  private final RateLimiterConfig config;

  public ThrottlingStrategyFactory(RateLimiterConfig config) {
    this.config = config;
  }

  public ThrottlingStrategy createThrottlingStrategy() {
    String strategy = config.getEnvironment().getProperty("ratelimiter.throttlingStrategy", "DELAY");
    int maxRetries = Integer.parseInt(config.getEnvironment().getProperty("ratelimiter.maxRetries", "3"));
    long delayMillis = Long.parseLong(config.getEnvironment().getProperty("ratelimiter.delayMillis", "1000"));
    double backoffFactor = Double.parseDouble(config.getEnvironment().getProperty("ratelimiter.backoffFactor", "2.0"));

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
}
