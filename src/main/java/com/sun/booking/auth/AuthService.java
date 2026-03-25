package com.sun.booking.auth;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseToken;
import com.sun.booking.auth.dto.UserDTO;
import com.sun.booking.common.Utils;
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
    @SuppressWarnings("unchecked")
    Map<String, Object> firebaseClaim = (Map<String, Object>) decodedToken.getClaims().get("firebase");
    String socialType = firebaseClaim != null ? (String) firebaseClaim.get("sign_in_provider") : null;
    String email = decodedToken.getEmail();
    String name = decodedToken.getName();
    String socialId = decodedToken.getUid();

    // Facebook may not provide email, fallback to uid-based email
    if (Utils.stringIsEmpty(email)) email = socialId + "@social.login";
    if (Utils.stringIsEmpty(name)) name = "user_" + socialId;

    // Find or create user in DB
    final String finalEmail = email;
    final String finalName = name;
    User user = userRepository.findBySocialId(socialId).orElseGet(() -> {
      User newUser = new User();
      newUser.setSocialId(socialId);
      newUser.setUsername(finalName);
      newUser.setEmail(finalEmail);
      newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
      newUser.setRole("USER");
      newUser.setSocialType(socialType);
      return userRepository.save(newUser);
    });

    // Generate JWT
    UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), List.of(user.getRole()));
    return jwtService.generateToken(userDTO);
  }
}
