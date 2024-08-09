package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.User;
import br.com.fiap.registropagamento.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = spy(new BCryptPasswordEncoder());
        userService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void authenticate_ValidCredentials_ReturnsUser() {
        User user = new User("testuser", "encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        User result = userService.authenticate("testuser", "password");

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void authenticate_InvalidUsername_ReturnsNull() {
        when(userRepository.findByUsername("invaliduser")).thenReturn(null);

        User result = userService.authenticate("invaliduser", "password");

        assertNull(result);
    }

    @Test
    void authenticate_InvalidPassword_ReturnsNull() {
        User user = new User("testuser", "encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        User result = userService.authenticate("testuser", "wrongpassword");

        assertNull(result);
    }

    @Test
    void saveUser_EncodesPasswordAndSavesUser() {
        User user = new User("testuser", "rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteAll_DeletesAllUsers() {
        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }
}