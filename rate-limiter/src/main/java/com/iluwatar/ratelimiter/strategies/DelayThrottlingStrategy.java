package com.iluwatar.ratelimiter.strategies;

import com.iluwatar.ratelimiter.ThrottlingAction;
import com.iluwatar.ratelimiter.ThrottlingStrategy;

import java.util.concurrent.TimeUnit;

/**
 * Delay Throttling Strategy implementation.
 * This strategy delays the request if the rate limit is exceeded, retrying after a certain period.
 */
public class DelayThrottlingStrategy implements ThrottlingStrategy {

  private final long delayMillis;

  /**
   * Constructs a DelayThrottlingStrategy.
   *
   * @param delayMillis The delay time in milliseconds before retrying the request.
   */
  public DelayThrottlingStrategy(long delayMillis){
    this.delayMillis = delayMillis;
  }

  @Override
  public ThrottlingAction apply(String clientId) {
    // Introduce a delay to throttle the request
    try {
      TimeUnit.MILLISECONDS.sleep(delayMillis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restore interrupted status
    }
    return ThrottlingAction.DELAY;
  }
}
