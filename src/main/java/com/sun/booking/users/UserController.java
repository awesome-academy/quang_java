package com.sun.booking.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;


@Tag(name = "User Management", description = "Endpoints for managing user profiles and information")
@RestController
@RequestMapping("/api/users")
public class UserController {

  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("info")
  public UserDTO getUserInfo(Authentication authentication) {
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

    String username = user.getUsername();

    Collection<? extends GrantedAuthority> roles = user.getAuthorities();

    UserDTO userDTO = new UserDTO();
    userDTO.setUsername(username);
    userDTO.setRoles(roles.stream().map(GrantedAuthority::getAuthority).toList());
    return userDTO;
  }
  
}
