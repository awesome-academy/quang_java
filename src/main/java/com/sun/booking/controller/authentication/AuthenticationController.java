package com.sun.booking.controller.authentication;

import com.sun.booking.common.Utils;
import com.sun.booking.users.User;
import com.sun.booking.users.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.booking.users.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequiredArgsConstructor
public class AuthenticationController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/social-login")
  public String socialLogin() {
    return "social-login";
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
  
}
