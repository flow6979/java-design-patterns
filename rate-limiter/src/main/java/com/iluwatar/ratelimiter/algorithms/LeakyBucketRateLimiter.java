package com.iluwatar.ratelimiter.algorithms;

import com.iluwatar.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Leaky Bucket Rate Limiter implementation.
 * This algorithm allows requests to be processed at a constant rate, even if bursts of requests are received.
 * Requests are added to a queue, and processed at a fixed rate, simulating a leaky bucket that leaks at a constant rate.
 */
public class LeakyBucketRateLimiter implements RateLimiter {

  private final int capacity;
  private final long leakIntervalMillis;
  private final AtomicLong availableSpace;
  private volatile Instant lastLeakTime;

  /**
   * Constructs a LeakyBucketRateLimiter.
   *
   * @param capacity The capacity of the bucket.
   * @param leakIntervalMillis The interval at which the bucket leaks in milliseconds.
   */
  public LeakyBucketRateLimiter(int capacity, long leakIntervalMillis) {
    this.capacity = capacity;
    this.leakIntervalMillis = leakIntervalMillis;
    this.availableSpace = new AtomicLong(capacity);
    this.lastLeakTime = Instant.now();
  }

  @Override
  public boolean tryAcquire(String clientId) {
    Instant now = Instant.now();
    leakBucket(now);
    return addRequest();
  }

  /**
   * Leaks requests from the bucket based on the elapsed time since the last leak.
   *
   * @param now The current time.
   */
  private void leakBucket(Instant now) {
    long elapsedMillis = now.toEpochMilli() - lastLeakTime.toEpochMilli();
    if (elapsedMillis >= leakIntervalMillis) {
      long leakCount = elapsedMillis / leakIntervalMillis;
      long newSpace = Math.min(capacity, availableSpace.get() + leakCount);
      availableSpace.set(newSpace);
      lastLeakTime = now;
    }
  }

  /**
   * Adds a request to the bucket if there is available space.
   *
   * @return true if the request was added, false otherwise.
   */
  private boolean addRequest() {
    long currentSpace = availableSpace.get();
    if (currentSpace > 0) {
      return availableSpace.compareAndSet(currentSpace, currentSpace - 1);
    }
    return false;
  }
}
