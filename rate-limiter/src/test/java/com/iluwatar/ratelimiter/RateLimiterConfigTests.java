package com.iluwatar.ratelimiter;

import com.iluwatar.ratelimiter.config.RateLimiterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the RateLimiterConfig class.
 */
public class RateLimiterConfigTests {

  private RateLimiterConfig rateLimiterConfig;

  @BeforeEach
  public void setUp() {
    // Initialize RateLimiterConfig with sample configuration data
    // In a real application, you might load from a properties file or other source
    rateLimiterConfig = new RateLimiterConfig();

    // Sample configuration values
    rateLimiterConfig.setAlgorithmByEndpoint("endpoint1", AlgorithmType.FIXED_WINDOW);
    rateLimiterConfig.setAlgorithmByEndpoint("endpoint2", AlgorithmType.SLIDING_WINDOW);
    rateLimiterConfig.setThrottlingStrategyByEndpointAndClientType("endpoint1", "STANDARD", ThrottlingStrategyType.DELAY);
    rateLimiterConfig.setThrottlingStrategyByEndpointAndClientType("endpoint2", "PREMIUM", ThrottlingStrategyType.REJECT);
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
