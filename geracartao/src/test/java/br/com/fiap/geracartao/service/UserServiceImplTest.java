package br.com.fiap.geracartao.service;


import br.com.fiap.geracartao.entity.User;
import br.com.fiap.geracartao.repository.UserRepository;
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

    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = mock(PasswordEncoder.class);
        userService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void authenticateReturnsUserWhenCredentialsAreCorrect() {
        User user = new User("username", "encodedPassword");
        when(userRepository.findByUsername("username")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        User result = userService.authenticate("username", "password");

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void authenticateReturnsNullWhenUserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        User result = userService.authenticate("username", "password");

        assertNull(result);
    }

    @Test
    void authenticateReturnsNullWhenPasswordIsIncorrect() {
        User user = new User("username", "encodedPassword");
        when(userRepository.findByUsername("username")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        User result = userService.authenticate("username", "password");

        assertNull(result);
    }

    @Test
    void saveUserEncodesPasswordAndSavesUser() {
        User user = new User("username", "password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteAllDeletesAllUsers() {
        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }
}