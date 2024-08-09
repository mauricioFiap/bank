package br.com.fiap.registrocliente.service;

import br.com.fiap.registrocliente.entity.User;
import br.com.fiap.registrocliente.repository.UserRepository;
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
    void authenticateReturnsUserWhenCredentialsAreCorrect() {
        User user = new User("testuser", "encodedPassword");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        User authenticatedUser = userService.authenticate("testuser", "password");

        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
    }

    @Test
    void authenticateReturnsNullWhenUserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        User authenticatedUser = userService.authenticate("nonexistentuser", "password");

        assertNull(authenticatedUser);
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }

    @Test
    void authenticateReturnsNullWhenPasswordIsIncorrect() {
        User user = new User("testuser", "encodedPassword");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        User authenticatedUser = userService.authenticate("testuser", "wrongpassword");

        assertNull(authenticatedUser);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("wrongpassword", "encodedPassword");
    }

    @Test
    void saveUserEncodesPasswordAndSavesUser() {
        User user = new User("testuser", "rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteAllDeletesAllUsers() {
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }
}