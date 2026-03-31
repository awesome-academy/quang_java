package com.sun.booking.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.jwt.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
  
  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
      CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

      UserDTO userDTO = new UserDTO();
      userDTO.setId(Long.parseLong(user.getId()));
      userDTO.setUsername(user.getUsername());
      userDTO.setEmail(user.getEmail());
      userDTO.setRoles(user.getAuthorities().stream().map(auth -> auth.getAuthority()).toList());                                
      
      String adminToken = jwtService.generateToken(userDTO);
      response.sendRedirect("/home?jwtToken=" + adminToken);
    }
  
}
