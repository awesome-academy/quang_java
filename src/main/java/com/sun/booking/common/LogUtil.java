package com.sun.booking.common;

import org.springframework.security.core.Authentication;

import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.security.CustomUserDetails;

public class LogUtil {

  public static String formatException(Exception ex) {
    StackTraceElement element = ex.getStackTrace()[0];

    return String.format(
            "%s.%s:%d - %s",
            element.getClassName(),
            element.getMethodName(),
            element.getLineNumber(),
            ex.getMessage()
    );
  }

  public UserDTO getCurrentUser(Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    UserDTO userDTO = new UserDTO();
    userDTO.setId(Long.parseLong(userDetails.getId()));
    userDTO.setUsername(userDetails.getUsername());
    userDTO.setEmail(userDetails.getEmail());
    return userDTO;
  }
}
