package com.sun.booking.jwt;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.booking.auth.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.expiration}")
  private Duration EXPIRATION_TIME;

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(UserDTO userInfo) {
      return Jwts.builder()
              .subject(userInfo.getId().toString())
              .claim("id", userInfo.getId().toString())
              .claim("username", userInfo.getUsername())
              .claim("email", userInfo.getEmail())
              .claim("roles", userInfo.getRoles())
              .issuedAt(new Date())
              .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME.toMillis()))
              .signWith(getSignKey())
              .compact();
  }

  public UserDTO extractUser(String token) {
    Claims claims = Jwts.parser()
            .verifyWith((SecretKey) getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    UserDTO userDTO = new UserDTO();
    userDTO.setId(Long.parseLong(claims.get("id", String.class)));
    userDTO.setUsername(claims.get("username", String.class));
    userDTO.setEmail(claims.get("email", String.class));
    Object rolesObj = claims.get("roles");

    if (rolesObj instanceof List<?>) {
        List<?> rawList = (List<?>) rolesObj;

        List<String> roles = rawList.stream()
                .map(Object::toString) // convert về String
                .toList();

        userDTO.setRoles(roles);
    }
    return userDTO;
  }

}
