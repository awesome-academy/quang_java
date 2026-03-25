package com.sun.booking.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.auth.dto.LoginResponseDTO;
import com.sun.booking.common.httpresponse.BaseResponse;
import com.sun.booking.common.httpresponse.SuccessResponse;
import com.sun.booking.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Authentication", description = "Endpoints for user authentication and social login")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/verify-token")
  public BaseResponse<LoginResponseDTO> verifyToken(@RequestBody Map<String, String> body) {
    String idToken = body.get("idToken");
    LoginResponseDTO response = new LoginResponseDTO(authService.verifyToken(idToken));
    return new SuccessResponse<LoginResponseDTO>(response);      
  }

}
