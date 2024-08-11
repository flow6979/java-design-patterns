package com.iluwatar.ratelimiter;

/**
 * Interface for throttling strategies that define how to handle requests
 * when the rate limit is exceeded.
 */
public interface ThrottlingStrategy {

  /**
   * Applies the throttling strategy to a request that exceeds the rate limit.
   *
   * @param clientId The identifier of the client making the request.
   * @return The action to take when the rate limit is exceeded.
   */
  ThrottlingAction apply(String clientId);
}
