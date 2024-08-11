package com.iluwatar.ratelimiter;

/**
 * Enum representing possible actions to take when the rate limit is exceeded.
 */
public enum ThrottlingAction {
  DELAY,                  // Delay the request and retry after a certain period.
  REJECT,                 // Reject the request immediately.
  EXPONENTIAL_BACKOFF     // Apply exponential backoff before retrying.
}
