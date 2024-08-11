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
    int maxRequests = Integer.parseInt(config.getEnvironment().getProperty("ratelimiter.maxRequests", "100"));
    long windowSizeMillis = Long.parseLong(config.getEnvironment().getProperty("ratelimiter.windowSizeMillis", "10000"));

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
}
