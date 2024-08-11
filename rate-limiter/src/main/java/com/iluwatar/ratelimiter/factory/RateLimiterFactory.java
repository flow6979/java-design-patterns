package com.iluwatar.ratelimiter.factory;

import com.iluwatar.ratelimiter.RateLimiter;
import com.iluwatar.ratelimiter.algorithms.FixedWindowRateLimiter;
import com.iluwatar.ratelimiter.algorithms.SlidingWindowRateLimiter;
import com.iluwatar.ratelimiter.algorithms.TokenBucketRateLimiter;
import com.iluwatar.ratelimiter.algorithms.LeakyBucketRateLimiter;
import com.iluwatar.ratelimiter.config.RateLimiterConfig;

public class RateLimiterFactory {

  private final RateLimiterConfig config;

  public RateLimiterFactory(RateLimiterConfig config) {
    this.config = config;
  }

  public RateLimiter createRateLimiter() {
    String algorithm = config.getEnvironment().getProperty("ratelimiter.algorithm", "FIXED_WINDOW");
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
}
