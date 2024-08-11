package com.iluwatar.ratelimiter.strategies;

import com.iluwatar.ratelimiter.ThrottlingAction;
import com.iluwatar.ratelimiter.ThrottlingStrategy;

/**
 * Reject Throttling Strategy implementation.
 * This strategy rejects the request immediately if the rate limit is exceeded.
 */
public class RejectThrottlingStrategy implements ThrottlingStrategy {

  @Override
  public ThrottlingAction apply(String clientId) {
    // Simply reject the request
    return ThrottlingAction.REJECT;
  }
}
