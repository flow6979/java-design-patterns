package com.iluwatar.ratelimiter;

public interface RateLimiter {
  boolean allowRequest(String clientId);
}
