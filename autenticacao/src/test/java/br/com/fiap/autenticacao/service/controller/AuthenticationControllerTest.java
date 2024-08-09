package br.com.fiap.autenticacao.service.controller;

import br.com.fiap.autenticacao.controller.AuthenticationController;
import br.com.fiap.autenticacao.entity.User;
import br.com.fiap.autenticacao.model.AuthenticationRequest;
import br.com.fiap.autenticacao.service.UserServiceImpl;
import br.com.fiap.autenticacao.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAuthenticationTokenWithValidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", "password");
        User user = new User("testuser", "password");

        when(userService.authenticate("testuser", "password")).thenReturn(user);
        when(jwtUtil.generateToken("testuser")).thenReturn("jwtToken");

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("jwtToken", response.getBody());
    }

    @Test
    void createAuthenticationTokenWithInvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("invaliduser", "password");

        when(userService.authenticate("invaliduser", "password")).thenReturn(null);

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void createAuthenticationTokenWithNullUser() {
        AuthenticationRequest request = new AuthenticationRequest(null, "password");

        when(userService.authenticate(null, "password")).thenReturn(null);

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void createAuthenticationTokenWithNullPassword() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", null);

        when(userService.authenticate("testuser", null)).thenReturn(null);

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }
}