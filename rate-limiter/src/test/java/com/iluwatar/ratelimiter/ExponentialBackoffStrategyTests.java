package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.strategies.ExponentialBackoffStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the ExponentialBackoffStrategy class.
 */
public class ExponentialBackoffStrategyTests {

  private ThrottlingStrategy throttlingStrategy;

  @BeforeEach
  public void setUp() {
    // Initialize ExponentialBackoffStrategy
    throttlingStrategy = new ExponentialBackoffStrategy(500); // Base delay of 500ms
  }

  @Test
  public void testApplyExponentialBackoffStrategy() {
    // Simulate applying the exponential backoff throttling strategy
    String clientId = "client1";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be EXPONENTIAL_BACKOFF
    assertEquals(ThrottlingAction.EXPONENTIAL_BACKOFF, action, "The action should be EXPONENTIAL_BACKOFF for ExponentialBackoffStrategy.");
  }

  @Test
  public void testApplyExponentialBackoffStrategyWithDifferentClient() {
    // Simulate applying the exponential backoff throttling strategy with a different client ID
    String clientId = "client2";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be EXPONENTIAL_BACKOFF regardless of client ID
    assertEquals(ThrottlingAction.EXPONENTIAL_BACKOFF, action, "The action should be EXPONENTIAL_BACKOFF for ExponentialBackoffStrategy regardless of client ID.");
  }
}
