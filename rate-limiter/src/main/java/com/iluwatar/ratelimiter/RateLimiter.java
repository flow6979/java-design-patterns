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

  /**
   * Set the rate limit for a specific client.
   *
   * @param clientId The identifier of the client for whom the rate limit is being set.
   * @param permitsPerSecond The number of permits allowed per second for this client.
   */
  void setRateLimit(String clientId, int permitsPerSecond);

  /**
   * Get the current rate limit for a specific client.
   *
   * @param clientId The identifier of the client for whom the rate limit is being retrieved.
   * @return The number of permits allowed per second for this client.
   */
  int getRateLimit(String clientId);
}
