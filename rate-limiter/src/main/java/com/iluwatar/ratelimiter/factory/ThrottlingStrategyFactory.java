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
