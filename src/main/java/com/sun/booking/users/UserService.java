package com.sun.booking.users;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sun.booking.common.httpresponse.ListResponse;
import com.sun.booking.users.dto.CreateRequest;
import com.sun.booking.users.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  
  public ListResponse getAllUsers(int page, int size) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<User> users = userRepository.findAllActive(pageable);
    List<UserDTO> content = users.stream()
        .map(user -> modelMapper.map(user, UserDTO.class))
        .collect(Collectors.toList());
    return ListResponse.builder()
        .content(content)
        .curPage(users.getNumber())
        .curPageSize(users.getSize())
        .totalElements(users.getTotalElements())
        .totalPages(users.getTotalPages())
        .build();
  }

  public UserDTO getUserDetail(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    return modelMapper.map(user, UserDTO.class);
  }

  public UserDTO createUser(CreateRequest userDTO) {
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setRole("USER");
    User savedUser = userRepository.save(user);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public UserDTO updateUser(Long id, UserDTO userDTO) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
  
    User updatedUser = userRepository.save(user);
    return modelMapper.map(updatedUser, UserDTO.class);
  }

  public boolean deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found with id: " + id);
    }
    int rowsAffected = userRepository.deleteUserById(id);
    return rowsAffected > 0;
  }

  public UserDTO getUserInfo(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    return modelMapper.map(user, UserDTO.class);
  }
}
