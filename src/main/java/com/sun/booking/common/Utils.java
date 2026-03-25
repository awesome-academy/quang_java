package com.sun.booking.common;

import org.springframework.security.core.Authentication;

import com.sun.booking.auth.dto.UserDTO;

public class Utils {
  public static boolean stringIsEmpty(String str) {
    return str == null || str.trim().isEmpty() || str.isBlank();
  }

  public static UserDTO getCurrentUser(Authentication authentication) {
    return new UserDTO(
      null,
      authentication.getName(),
      null,
      authentication.getAuthorities().stream()
      .map(auth -> auth.getAuthority())
      .filter(auth -> auth.startsWith("ROLE_"))
      .toList()
    );
  }
}
