package com.iluwatar.ratelimiter;

/**
 * Interface for a rate limiter that controls the rate of requests.
 */
public interface RateLimiter {

  /**
   * Attempts to acquire a permit to proceed with the request.
   *
   * @param clientId The identifier of the client making the request.
   * @return true if the request is allowed, false if it exceeds the rate limit.
   */
  boolean tryAcquire(String clientId);
}
