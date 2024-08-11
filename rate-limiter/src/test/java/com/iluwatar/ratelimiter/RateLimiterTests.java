package com.iluwatar.ratelimiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the RateLimiter interface.
 */
public class RateLimiterTests {

  private RateLimiter rateLimiter;

  @BeforeEach
  public void setUp() {
    // Initialize the RateLimiter with a mock or a real implementation
    // For example, using FixedWindowRateLimiter:
    rateLimiter = new FixedWindowRateLimiter(10, 60000); // 10 requests per 60 seconds
  }

  @Test
  public void testRateLimiterWithinLimit() {
    // Simulate requests within the rate limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed within the limit.");
    }
  }

  @Test
  public void testRateLimiterExceedsLimit() {
    // Simulate exceeding the rate limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Consume all allowed requests
    }
    // Now all additional requests should be rejected
    assertFalse(rateLimiter.tryAcquire(clientId), "Request should be denied when limit is exceeded.");
  }

  @Test
  public void testRateLimiterResetsAfterWindow() throws InterruptedException {
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
