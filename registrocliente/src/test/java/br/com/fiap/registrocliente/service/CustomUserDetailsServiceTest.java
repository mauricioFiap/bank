package br.com.fiap.registrocliente.service;


import br.com.fiap.registrocliente.entity.User;
import br.com.fiap.registrocliente.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        User user = new User("testuser", "password");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("nonexistentuser"));
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUsernameIsNull() {
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(null));
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUsernameIsEmpty() {
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(""));
    }
}
