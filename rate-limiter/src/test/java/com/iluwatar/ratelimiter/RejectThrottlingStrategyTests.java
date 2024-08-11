package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.strategies.RejectThrottlingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the RejectThrottlingStrategy class.
 */
public class RejectThrottlingStrategyTests {

  private ThrottlingStrategy throttlingStrategy;

  @BeforeEach
  public void setUp() {
    // Initialize RejectThrottlingStrategy
    throttlingStrategy = new RejectThrottlingStrategy();
  }

  @Test
  public void testApplyRejectThrottlingStrategy() {
    // Simulate applying the reject throttling strategy
    String clientId = "client1";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be REJECT
    assertEquals(ThrottlingAction.REJECT, action, "The action should be REJECT for RejectThrottlingStrategy.");
  }

  @Test
  public void testApplyRejectThrottlingStrategyWithDifferentClient() {
    // Simulate applying the reject throttling strategy with a different client ID
    String clientId = "client2";
    ThrottlingAction action = throttlingStrategy.apply(clientId);

    // The action should be REJECT regardless of client ID
    assertEquals(ThrottlingAction.REJECT, action, "The action should be REJECT for RejectThrottlingStrategy regardless of client ID.");
  }
}
