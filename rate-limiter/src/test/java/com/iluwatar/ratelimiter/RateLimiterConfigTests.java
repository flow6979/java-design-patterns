package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.config.RateLimiterConfig;
import com.iluwatar.ratelimiter.config.AlgorithmType;
import com.iluwatar.ratelimiter.config.ThrottlingStrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the RateLimiterConfig class.
 */
public class RateLimiterConfigTests {

  private RateLimiterConfig rateLimiterConfig;

  @BeforeEach
  public void setUp() {
    // Create a mock Environment object
    Environment env = Mockito.mock(Environment.class);
    // Initialize RateLimiterConfig with sample configuration data
    rateLimiterConfig = new RateLimiterConfig(env);

    // Configure sample settings
    Mockito.when(env.getProperty("rate.limiter.algorithm.endpoint1")).thenReturn("FIXED_WINDOW");
    Mockito.when(env.getProperty("rate.limiter.algorithm.endpoint2")).thenReturn("SLIDING_WINDOW");
    Mockito.when(env.getProperty("rate.limiter.throttling.endpoint1.standard")).thenReturn("DELAY");
    Mockito.when(env.getProperty("rate.limiter.throttling.endpoint2.premium")).thenReturn("REJECT");
  }

  @Test
  public void testGetAlgorithmByEndpoint() {
    // Test retrieving algorithm type by endpoint
    AlgorithmType algorithm = rateLimiterConfig.getAlgorithmByEndpoint("endpoint1");
    assertEquals(AlgorithmType.FIXED_WINDOW, algorithm, "The algorithm for endpoint1 should be FIXED_WINDOW.");

    algorithm = rateLimiterConfig.getAlgorithmByEndpoint("endpoint2");
    assertEquals(AlgorithmType.SLIDING_WINDOW, algorithm, "The algorithm for endpoint2 should be SLIDING_WINDOW.");
  }

  @Test
  public void testGetThrottlingStrategyByEndpointAndClientType() {
    // Test retrieving throttling strategy by endpoint and client type
    ThrottlingStrategyType strategy = rateLimiterConfig.getThrottlingStrategyByEndpointAndClientType("endpoint1", "STANDARD");
    assertEquals(ThrottlingStrategyType.DELAY, strategy, "The throttling strategy for endpoint1 and STANDARD should be DELAY.");

    strategy = rateLimiterConfig.getThrottlingStrategyByEndpointAndClientType("endpoint2", "PREMIUM");
    assertEquals(ThrottlingStrategyType.REJECT, strategy, "The throttling strategy for endpoint2 and PREMIUM should be REJECT.");
  }
}
