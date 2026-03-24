package com.sun.booking.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyAuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
			String error = "unknown";
			if (exception instanceof BadCredentialsException) {
				error = "invalid username or password";
			} else if (exception instanceof LockedException) {
				error = "account locked";
			} else if (exception instanceof DisabledException) {
				error = "account disabled";
			}
			response.sendRedirect("/login?error=" + error);
		}
}
