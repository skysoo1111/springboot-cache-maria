package com.study.cachedemo.config;

import lombok.Getter;

@Getter
public enum CacheType {
  USERS("users", 5*60, 10000),
  USER_INFO("user_info",24*60*60, 10000);

  CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
    this.cacheName = cacheName;
    this.expiredAfterWrite = expiredAfterWrite;
    this.maximumSize = maximumSize;
  }

  private String cacheName;
  private int expiredAfterWrite;
  private int maximumSize;
}
