package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.strategies.DelayThrottlingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the DelayThrottlingStrategy class.
 */
public class DelayThrottlingStrategyTests {

  private ThrottlingStrategy throttlingStrategy;

  @BeforeEach
  public void setUp() {
    // Initialize DelayThrottlingStrategy
    throttlingStrategy = new DelayThrottlingStrategy(1000); // 1 second delay
  }

  @Test
  public void testApplyDelayThrottlingStrategy() {
    // Simulate applying the delay throttling strategy
    String clientId = "client1";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be DELAY
    assertEquals(ThrottlingAction.DELAY, action, "The action should be DELAY for DelayThrottlingStrategy.");
  }

  @Test
  public void testApplyDelayThrottlingStrategyWithDifferentClient() {
    // Simulate applying the delay throttling strategy with a different client ID
    String clientId = "client2";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be DELAY regardless of client ID
    assertEquals(ThrottlingAction.DELAY, action, "The action should be DELAY for DelayThrottlingStrategy regardless of client ID.");
  }
}
