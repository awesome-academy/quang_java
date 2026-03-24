package com.sun.booking.auth;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseToken;
import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.firebase.FirebaseService;
import com.sun.booking.jwt.JwtService;
import com.sun.booking.users.User;
import com.sun.booking.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final FirebaseService firebaseService;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public String verifyToken(String idToken) {
    FirebaseToken decodedToken = firebaseService.verifyToken(idToken);
    if (decodedToken == null) {
      return null;
    }

    String email = decodedToken.getEmail();
    String name = decodedToken.getName();

    // Find or create user in DB
    User user = userRepository.findByEmail(email).orElseGet(() -> {
      User newUser = new User();
      newUser.setUsername(name != null ? name : email);
      newUser.setEmail(email);
      newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
      newUser.setRole("USER");
      return userRepository.save(newUser);
    });

    // Generate JWT
    UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), List.of(user.getRole()));
    return jwtService.generateToken(userDTO);
  }
}
