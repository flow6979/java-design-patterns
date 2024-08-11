package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.algorithms.TokenBucketRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the TokenBucketRateLimiter class.
 */
public class TokenBucketRateLimiterTests {

  private RateLimiter rateLimiter;

  @BeforeEach
  public void setUp() {
    // Initialize TokenBucketRateLimiter with a capacity of 10 tokens and a refill rate of 1 token per second
    rateLimiter = new TokenBucketRateLimiter(10, 1000); // 10 tokens and 1 second refill interval
  }

  @Test
  public void testTokenBucketRateLimiterWithinLimit() {
    // Simulate requests within the token bucket limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed within the token bucket limit.");
    }
  }

  @Test
  public void testTokenBucketRateLimiterExceedsLimit() {
    // Simulate exceeding the token bucket limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Consume all available tokens
    }
    // Additional requests should be denied until tokens are refilled
    assertFalse(rateLimiter.tryAcquire(clientId), "Request should be denied when tokens are exhausted.");
  }

  @Test
  public void testTokenBucketRateLimiterRefillsOverTime() throws InterruptedException {
    // Simulate requests and wait for tokens to refill
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Consume all available tokens
    }
    // Wait for tokens to refill (1 second)
    Thread.sleep(1000);
    // After waiting, requests should be allowed again if tokens are available
    assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed after tokens are refilled.");
  }
}
