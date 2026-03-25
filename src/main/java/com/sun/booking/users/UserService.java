package com.sun.booking.users;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.sun.booking.users.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  
  public UserDTO getUserInfo(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    return modelMapper.map(user, UserDTO.class);
  }
}
