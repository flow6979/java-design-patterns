package com.iluwatar.ratelimiter.algorithms;

import com.iluwatar.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Token Bucket Rate Limiter implementation.
 * This algorithm allows a certain number of requests to pass through in a given time interval.
 * The rate limiter fills tokens into the bucket at a constant rate and allows requests if there are tokens available.
 */
public class TokenBucketRateLimiter implements RateLimiter {

  private final int maxTokens;
  private final long refillIntervalMillis;
  private final AtomicLong availableTokens;
  private volatile Instant lastRefillTime;

  /**
   * Constructs a TokenBucketRateLimiter.
   *
   * @param maxTokens The maximum number of tokens in the bucket.
   * @param refillIntervalMillis The interval at which tokens are refilled in milliseconds.
   */
  public TokenBucketRateLimiter(int maxTokens, long refillIntervalMillis) {
    this.maxTokens = maxTokens;
    this.refillIntervalMillis = refillIntervalMillis;
    this.availableTokens = new AtomicLong(maxTokens);
    this.lastRefillTime = Instant.now();
  }

  @Override
  public boolean tryAcquire(String clientId) {
    Instant now = Instant.now();
    refillTokens(now);
    return consumeToken();
  }

  /**
   * Refills tokens in the bucket based on the elapsed time since the last refill.
   *
   * @param now The current time.
   */
  private void refillTokens(Instant now) {
    long elapsedMillis = now.toEpochMilli() - lastRefillTime.toEpochMilli();
    if (elapsedMillis >= refillIntervalMillis) {
      long newTokens = elapsedMillis / refillIntervalMillis;
      long tokensToAdd = Math.min(newTokens, maxTokens - availableTokens.get());
      availableTokens.addAndGet(tokensToAdd);
      lastRefillTime = now;
    }
  }

  /**
   * Consumes a token if available.
   *
   * @return true if a token was successfully consumed, false otherwise.
   */
  private boolean consumeToken() {
    long currentTokens = availableTokens.get();
    if (currentTokens > 0) {
      return availableTokens.compareAndSet(currentTokens, currentTokens - 1);
    }
    return false;
  }
}
