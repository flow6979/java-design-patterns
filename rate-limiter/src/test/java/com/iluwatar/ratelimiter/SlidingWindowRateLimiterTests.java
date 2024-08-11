package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.algorithms.SlidingWindowRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the SlidingWindowRateLimiter class.
 */
public class SlidingWindowRateLimiterTests {

  private RateLimiter rateLimiter;

  @BeforeEach
  public void setUp() {
    // Initialize SlidingWindowRateLimiter with a limit of 10 requests per 60 seconds
    rateLimiter = new SlidingWindowRateLimiter(10, 60000);
  }

  @Test
  public void testSlidingWindowRateLimiterWithinLimit() {
    // Simulate requests within the rate limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed within the limit.");
    }
  }

  @Test
  public void testSlidingWindowRateLimiterExceedsLimit() {
    // Simulate exceeding the rate limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Consume all allowed requests
    }
    // Now additional requests might be rejected depending on the sliding window implementation
    // To make this test reliable, you might need to simulate time passing or adjust implementation
    assertFalse(rateLimiter.tryAcquire(clientId), "Request should be denied when limit is exceeded.");
  }

  @Test
  public void testSlidingWindowRateLimiterResetsAfterWindow() throws InterruptedException {
    // Simulate requests and wait for the window to reset
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Consume all allowed requests
    }
    // Wait for the window to reset (60 seconds)
    Thread.sleep(60000);
    // After waiting, requests should be allowed again
    assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed after window reset.");
  }
}
