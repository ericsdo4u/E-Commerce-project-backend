package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LoginRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LogoutRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.RegisterRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.UserResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.User;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  
  private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered.";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .loggedIn(false)
                .build();

        userRepository.save(user);
        return "Registration successful.";
    }

    @Override
    public String login(LoginRequestDto request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(request.getPassword())) {
                user.setLoggedIn(true);
                userRepository.save(user);
                return "Login successful.";
            } else {
                return "Invalid password.";
            }
        } else {
            return "User not found.";
        }
    }

    @Override
    public String logout(LogoutRequestDto request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isLoggedIn()) {
                user.setLoggedIn(false);
                userRepository.save(user);
                return "Logout successful.";
            } else {
                return "User is not logged in.";
            }
        } else {
            return "User not found.";
        }
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .loggedIn(user.isLoggedIn())
                        .build())
                .orElse(null);
    }
  
}
