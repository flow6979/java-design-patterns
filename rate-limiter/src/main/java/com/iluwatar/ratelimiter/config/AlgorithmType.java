package com.iluwatar.ratelimiter.config;

/**
 * Enum for different rate limiting algorithms.
 */
public enum AlgorithmType {
  FIXED_WINDOW,
  SLIDING_WINDOW,
  TOKEN_BUCKET,
  LEAKY_BUCKET
}
