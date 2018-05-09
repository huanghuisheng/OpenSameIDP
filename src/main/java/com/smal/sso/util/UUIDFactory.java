package com.smal.sso.util;

import java.util.UUID;

/**
 * @author Sunny
 */
public enum UUIDFactory {
  
  INSTANCE;
  
  public synchronized String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}