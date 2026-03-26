package com.sun.booking.common;

import org.springframework.security.core.Authentication;

import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.security.CustomUserDetails;

public class Utils {
  public static boolean stringIsEmpty(String str) {
    return str == null || str.trim().isEmpty() || str.isBlank();
  }

  public static UserDTO getCurrentUser(Authentication authentication) {
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
      return new UserDTO(
        Long.parseLong(user.getId()),
        user.getUsername(),
        user.getEmail(),
        user.getAuthorities().stream()
        .map(auth -> auth.getAuthority())
        .filter(auth -> auth.startsWith("ROLE_"))
        .toList()
    );
  }
}
