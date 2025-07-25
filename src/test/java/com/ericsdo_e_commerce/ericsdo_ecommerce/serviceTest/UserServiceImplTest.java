package com.ericsdo_e_commerce.ericsdo_ecommerce.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LoginRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.LogoutRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.RegisterRequestDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.dtos.UserResponseDto;
import com.ericsdo_e_commerce.ericsdo_ecommerce.model.User;
import com.ericsdo_e_commerce.ericsdo_ecommerce.repositories.UserRepository;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.UserService;
import com.ericsdo_e_commerce.ericsdo_ecommerce.services.UserServiceImpl;

// @DataJpaTest
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceImplTest {
   private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testRegister_Success() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setName("ddon");
        dto.setEmail("ddon@example.com");
        dto.setPassword("password");

        String response = userService.register(dto);
        assertEquals("Registration successful.", response);
        assertTrue(userRepository.findByEmail("ddon@example.com").isPresent());
    }

    @Test
    public void testRegister_EmailExists() {
        User existingUser = User.builder()
                .name("don")
                .email("don@example.com")
                .password("secret")
                .loggedIn(false)
                .build();
        userRepository.save(existingUser);

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setName("don");
        dto.setEmail("don@example.com");
        dto.setPassword("secret");

        String response = userService.register(dto);
        assertEquals("Email already registered.", response);
    }

    @Test
    public void testLogin_Success() {
        User user = User.builder()
                .name("Sam")
                .email("sam@example.com")
                .password("pass123")
                .loggedIn(false)
                .build();
        userRepository.save(user);

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("sam@example.com");
        dto.setPassword("pass123");

        String response = userService.login(dto);
        assertEquals("Login successful.", response);

        Optional<User> loggedInUser = userRepository.findByEmail("sam@example.com");
        assertTrue(loggedInUser.get().isLoggedIn());
    }

    @Test
    public void testLogin_WrongPassword() {
        User user = User.builder()
                .name("joy")
                .email("joy@example.com")
                .password("realpass")
                .loggedIn(false)
                .build();
        userRepository.save(user);

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("joy@example.com");
        dto.setPassword("wrongpass");

        String response = userService.login(dto);
        assertEquals("Invalid password.", response);
    }

    @Test
    public void testLogout_Success() {
        User user = User.builder()
                .name("Alan")
                .email("alan@example.com")
                .password("test")
                .loggedIn(true)
                .build();
        userRepository.save(user);

        LogoutRequestDto dto = new LogoutRequestDto();
        dto.setEmail("alan@example.com");

        String response = userService.logout(dto);
        assertEquals("Logout successful.", response);

        Optional<User> loggedOutUser = userRepository.findByEmail("alan@example.com");
        assertFalse(loggedOutUser.get().isLoggedIn());
    }

    @Test
    public void testGetUserByEmail() {
        User user = User.builder()
                .name("Tom")
                .email("tom@example.com")
                .password("12345")
                .loggedIn(false)
                .build();
        userRepository.save(user);

        UserResponseDto dto = userService.getUserByEmail("tom@example.com");

        assertNotNull(dto);
        assertEquals("Tom", dto.getName());
        assertEquals("tom@example.com", dto.getEmail());
    }
}