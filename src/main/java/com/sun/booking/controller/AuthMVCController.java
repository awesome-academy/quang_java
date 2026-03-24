package com.sun.booking.controller;

import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.common.Utils;
import com.sun.booking.jwt.JwtService;
import com.sun.booking.security.CustomUserDetails;
import com.sun.booking.users.User;
import com.sun.booking.users.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.booking.users.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequiredArgsConstructor
public class AuthMVCController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @GetMapping("/user-login")
  public String userLogin() {
    return "user-login";
  }

  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("loginError", true);
      model.addAttribute("errorMessage", error);
    }
    return "login";
  }

  @GetMapping("/register")
  public String register(@ModelAttribute RegisterRequest request, Model model) {
    if(Utils.stringIsEmpty(request.getUsername()) || Utils.stringIsEmpty(request.getPassword())){
      return "register";
    }

    if(userRepository.existsByUsername(request.getUsername())){
      model.addAttribute("registerError", true);
      model.addAttribute("errorMessage", "Username already exists");
      return "register";
    }
    
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole("USER");

    userRepository.save(user);

    return "redirect:/login";
  }
  
  @PostMapping("/logout")
  public String logout() {
    return "redirect:/login";
  }

  @PostMapping("/auth/social-callback")
  public String socialCallback(@RequestParam("token") String token,
                               HttpServletRequest request,
                               HttpServletResponse response) {
    UserDTO userDTO;
    try {
      userDTO = jwtService.extractUser(token);
    } catch (Exception e) {
      return "redirect:/login?error=Invalid token";
    }

    if (userDTO == null) {
      return "redirect:/login?error=Invalid token";
    }

    List<GrantedAuthority> authorities = userDTO.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        .collect(Collectors.toList());

    CustomUserDetails userDetails = new CustomUserDetails(
        userDTO.getId().toString(),
        userDTO.getUsername(),
        userDTO.getEmail(),
        null,
        authorities
    );

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);

    new HttpSessionSecurityContextRepository().saveContext(context, request, response);

    return "redirect:/home";
  }
}
