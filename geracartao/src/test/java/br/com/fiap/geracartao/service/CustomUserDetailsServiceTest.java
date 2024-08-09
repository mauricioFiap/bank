package br.com.fiap.geracartao.service;

import br.com.fiap.geracartao.entity.User;
import br.com.fiap.geracartao.repository.UserRepository;
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
        User user = new User("username", "password");
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("username");

        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("username");
        });
    }
}