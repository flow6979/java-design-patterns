package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.algorithms.LeakyBucketRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the LeakyBucketRateLimiter class.
 */
public class LeakyBucketRateLimiterTests {

  private RateLimiter rateLimiter;

  @BeforeEach
  public void setUp() {
    // Initialize LeakyBucketRateLimiter with a capacity of 10 requests and a leak rate of 1 request per second
    rateLimiter = new LeakyBucketRateLimiter(10, 1000); // 10 requests capacity and 1 second leak rate
  }

  @Test
  public void testLeakyBucketRateLimiterWithinLimit() {
    // Simulate requests within the leaky bucket limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed within the leaky bucket limit.");
    }
  }

  @Test
  public void testLeakyBucketRateLimiterExceedsLimit() {
    // Simulate exceeding the leaky bucket limit
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Fill the bucket
    }
    // Additional requests should be denied until the bucket leaks
    assertFalse(rateLimiter.tryAcquire(clientId), "Request should be denied when the leaky bucket is full.");
  }

  @Test
  public void testLeakyBucketRateLimiterLeaksOverTime() throws InterruptedException {
    // Simulate requests and wait for the bucket to leak
    String clientId = "client1";
    for (int i = 0; i < 10; i++) {
      rateLimiter.tryAcquire(clientId); // Fill the bucket
    }
    // Wait for some time to allow the bucket to leak (1 second)
    Thread.sleep(1000);
    // After waiting, requests should be allowed again if the bucket has leaked
    assertTrue(rateLimiter.tryAcquire(clientId), "Request should be allowed after the bucket has leaked.");
  }
}
