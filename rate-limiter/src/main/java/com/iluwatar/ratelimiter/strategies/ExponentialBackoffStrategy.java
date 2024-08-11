package com.iluwatar.ratelimiter.strategies;

import com.iluwatar.ratelimiter.ThrottlingAction;
import com.iluwatar.ratelimiter.ThrottlingStrategy;

import java.util.Random;

/**
 * Exponential Backoff Throttling Strategy implementation.
 * This strategy applies exponential backoff before retrying the request if the rate limit is exceeded.
 */
public class ExponentialBackoffStrategy implements ThrottlingStrategy {

  private final Random random = new Random();
  private final int maxRetries;
  private final long initialDelayMillis;
  private final double multiplier;

  /**
   * Constructs an ExponentialBackoffStrategy.
   *
   * @param maxRetries The maximum number of retries allowed.
   * @param initialDelayMillis The initial delay between retries in milliseconds.
   * @param multiplier The multiplier for exponential backoff.
   */
  public ExponentialBackoffStrategy(int maxRetries, long initialDelayMillis, double multiplier) {
    this.maxRetries = maxRetries;
    this.initialDelayMillis = initialDelayMillis;
    this.multiplier = multiplier;
  }

  @Override
  public ThrottlingAction apply(String clientId) {
    // Compute the delay before retrying
    int retryCount = getRetryCount(clientId);
    if (retryCount >= maxRetries) {
      return ThrottlingAction.REJECT;
    }

    long delay = computeDelay(retryCount);
    // You would typically schedule a retry with the computed delay here
    // For simplicity, we're just returning the action

    return ThrottlingAction.EXPONENTIAL_BACKOFF;
  }

  /**
   * Computes the delay before retrying based on the retry count.
   *
   * @param retryCount The number of retries attempted.
   * @return The delay in milliseconds.
   */
  private long computeDelay(int retryCount) {
    return (long) (initialDelayMillis * Math.pow(multiplier, retryCount) + random.nextInt(1000));
  }

  /**
   * Placeholder method to get the retry count for a client.
   * In a real implementation, this should track the number of retries.
   *
   * @param clientId The identifier of the client.
   * @return The retry count.
   */
  private int getRetryCount(String clientId) {
    // This should be implemented to track the actual retry count for each client
    return 0; // Placeholder
  }
}
