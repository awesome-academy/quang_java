package com.sun.booking.common;

public class Utils {
  public static boolean stringIsEmpty(String str) {
    return str == null || str.trim().isEmpty() || str.isBlank();
  }
}
