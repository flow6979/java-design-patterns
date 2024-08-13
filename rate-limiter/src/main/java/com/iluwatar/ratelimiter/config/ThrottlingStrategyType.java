package com.iluwatar.ratelimiter.config;

/**
 * Enum for different throttling strategies.
 */
public enum ThrottlingStrategyType {
  DELAY,
  REJECT,
  EXPONENTIAL_BACKOFF
}
