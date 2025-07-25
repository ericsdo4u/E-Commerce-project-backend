package com.ericsdo_e_commerce.ericsdo_ecommerce.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
  private Long id;
    private String name;
    private String email;
    private boolean loggedIn;
}
