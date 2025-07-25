package com.ericsdo_e_commerce.ericsdo_ecommerce.services;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LoginRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LogoutRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.RegisterRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.UserResponseDto;

public interface UserService {
  
  String register(RegisterRequestDto registerRequest);
  String login(LoginRequestDto loginRequest);
  String logout(LogoutRequestDto logoutRequest);
  UserResponseDto getUserByEmail(String email);
  }
