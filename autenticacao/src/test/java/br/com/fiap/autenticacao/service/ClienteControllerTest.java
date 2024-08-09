package br.com.fiap.autenticacao.service;


import br.com.fiap.autenticacao.entity.User;
import br.com.fiap.autenticacao.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = spy(PasswordEncoder.class);
        userService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void authenticateWithValidCredentials() {
        User user = new User("testuser", "encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        User result = userService.authenticate("testuser", "password");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void authenticateWithInvalidUsername() {
        when(userRepository.findByUsername("invaliduser")).thenReturn(null);

        User result = userService.authenticate("invaliduser", "password");

        assertNull(result);
    }

    @Test
    void authenticateWithInvalidPassword() {
        User user = new User("testuser", "encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        User result = userService.authenticate("testuser", "wrongpassword");

        assertNull(result);
    }

    @Test
    void saveUserEncodesPassword() {
        User user = new User("testuser", "rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void deleteAllUsers() {
        userService.deleteAll();

        verify(userRepository).deleteAll();
    }
}
