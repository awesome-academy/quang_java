package com.sun.booking.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.booking.common.httpresponse.BaseResponse;
import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.common.httpresponse.SuccessResponse;
import com.sun.booking.security.CustomUserDetails;
import com.sun.booking.users.dto.CreateRequest;
import com.sun.booking.users.dto.UserDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Tag(name = "User Management", description = "Endpoints for managing user profiles and information")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/")
  public BaseResponse<ListResponse> getListUser(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
      ListResponse result = userService.getAllUsers(page, size);
      return new SuccessResponse<ListResponse>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/detail/{id}")
  public BaseResponse<UserDTO> getUserDetail(@PathVariable Long id) {
    UserDTO result = userService.getUserDetail(id);
    return new SuccessResponse<UserDTO>(result);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping("/create")
  public BaseResponse<UserDTO> createUser(@Valid @RequestBody CreateRequest userDTO) {
    UserDTO result = userService.createUser(userDTO);
    return new SuccessResponse<UserDTO>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @PutMapping("/update/{id}")
  public BaseResponse<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
    UserDTO result = userService.updateUser(id, userDTO);
    return new SuccessResponse<UserDTO>(result);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @DeleteMapping("/delete/{id}")
  public BaseResponse<Boolean> deleteUser(@PathVariable Long id) {
    boolean result = userService.deleteUser(id);
    return new SuccessResponse<Boolean>(result);
  }


  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("info")
  public com.sun.booking.auth.dto.UserDTO getUserInfo(Authentication authentication) {
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

    String username = user.getUsername();

    Collection<? extends GrantedAuthority> roles = user.getAuthorities();

    com.sun.booking.auth.dto.UserDTO userDTO = new com.sun.booking.auth.dto.UserDTO();
    userDTO.setUsername(username);
    userDTO.setRoles(roles.stream().map(GrantedAuthority::getAuthority).toList());
    return userDTO;
  }
  
}
