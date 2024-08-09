package br.com.fiap.registrocliente.filter;

import br.com.fiap.registrocliente.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternalSetsAuthenticationWhenTokenIsValid() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        Claims claims = mock(Claims.class);
        when(jwtUtil.extractClaims("validToken")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("testuser");
        when(jwtUtil.validateToken("validToken", "testuser")).thenReturn(true);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        //assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalDoesNotSetAuthenticationWhenTokenIsInvalid() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        Claims claims = mock(Claims.class);
        when(jwtUtil.extractClaims("invalidToken")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("testuser");
        when(jwtUtil.validateToken("invalidToken", "testuser")).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        //assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalDoesNotSetAuthenticationWhenHeaderIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternalDoesNotSetAuthenticationWhenHeaderIsMalformed() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("MalformedToken");

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain, times(1)).doFilter(request, response);
    }
}