package com.ericsdo_e_commerce.ericsdo_ecommerce.dtos;

import lombok.Data;

@Data
public class RegisterRequestDto {
  private String name;
    private String email;
    private String password;
}
