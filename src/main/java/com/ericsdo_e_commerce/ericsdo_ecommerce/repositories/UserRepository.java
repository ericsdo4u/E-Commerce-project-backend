package com.ericsdo_e_commerce.ericsdo_ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsdo_e_commerce.ericsdo_ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

   Optional<User> findByEmail(String email);
  
}
