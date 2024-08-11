package com.iluwatar.ratelimiter.algorithms;

import com.iluwatar.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Sliding Window Rate Limiter implementation.
 * This algorithm maintains a sliding window of requests and limits the number of requests within that window.
 */
public class SlidingWindowRateLimiter implements RateLimiter {

  private final int maxRequests;
  private final long windowSizeMillis;
  private final Map<String, RequestWindow> requestWindows = new HashMap<>();

  /**
   * Constructs a SlidingWindowRateLimiter.
   *
   * @param maxRequests The maximum number of requests allowed in the sliding window.
   * @param windowSizeMillis The size of the sliding window in milliseconds.
   */
  public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis) {
    this.maxRequests = maxRequests;
    this.windowSizeMillis = windowSizeMillis;
  }

  @Override
  public boolean tryAcquire(String clientId) {
    RequestWindow window = requestWindows.computeIfAbsent(clientId, id -> new RequestWindow());
    synchronized (window) {
      Instant now = Instant.now();
      window.cleanup(now);
      if (window.getRequestCount(now) < maxRequests) {
        window.addRequest(now);
        return true;
      }
      return false;
    }
  }

  /**
   * Inner class to keep track of requests for a specific client.
   */
  private class RequestWindow {
    private final LinkedList<Instant> requests = new LinkedList<>();

    /**
     * Adds a request to the window.
     *
     * @param timestamp The timestamp of the request.
     */
    void addRequest(Instant timestamp) {
      requests.addLast(timestamp);
    }

    /**
     * Returns the count of requests in the current window.
     *
     * @param now The current time.
     * @return The number of requests in the current window.
     */
    int getRequestCount(Instant now) {
      cleanup(now);
      return requests.size();
    }

    /**
     * Removes requests outside the current window.
     *
     * @param now The current time.
     */
    void cleanup(Instant now) {
      Instant windowStart = now.minusMillis(windowSizeMillis);
      while (!requests.isEmpty() && requests.getFirst().isBefore(windowStart)) {
        requests.removeFirst();
      }
    }
  }
}
