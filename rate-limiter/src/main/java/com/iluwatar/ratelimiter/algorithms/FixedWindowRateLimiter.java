package com.iluwatar.ratelimiter.algorithms;

import com.iluwatar.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Fixed Window Rate Limiter implementation.
 * This algorithm allows a fixed number of requests per time window (e.g., per minute).
 * Requests exceeding the limit within the same time window are denied.
 */
public class FixedWindowRateLimiter implements RateLimiter {

  private final int maxRequests;
  private final long windowSizeMillis;
  private final ConcurrentMap<String, Window> clientWindows;

  /**
   * Constructs a FixedWindowRateLimiter.
   *
   * @param maxRequests The maximum number of requests allowed in the time window.
   * @param windowSizeMillis The size of the time window in milliseconds.
   */
  public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
    this.maxRequests = maxRequests;
    this.windowSizeMillis = windowSizeMillis;
    this.clientWindows = new ConcurrentHashMap<>();
  }

  @Override
  public boolean tryAcquire(String clientId) {
    Instant now = Instant.now();
    Window window = clientWindows.computeIfAbsent(clientId, id -> new Window(now));

    synchronized (window) {
      if (now.toEpochMilli() - window.startTime.toEpochMilli() > windowSizeMillis) {
        // Reset the window
        window.startTime = now;
        window.requestCount = 0;
      }

      if (window.requestCount < maxRequests) {
        window.requestCount++;
        return true;
      } else {
        return false;
      }
    }
  }

  /**
   * Inner class representing a time window for a client.
   */
  private static class Window {
    Instant startTime;
    int requestCount;

    Window(Instant startTime) {
      this.startTime = startTime;
      this.requestCount = 0;
    }
  }
}
