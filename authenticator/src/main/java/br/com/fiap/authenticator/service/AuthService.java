package br.com.fiap.authenticator.service;

import br.com.fiap.authenticator.request.AuthenticationRequest;
import br.com.fiap.authenticator.response.AuthenticationResponse;
import br.com.fiap.authenticator.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String jwt = jwtUtils.generateToken(authentication.getName());
        return new AuthenticationResponse(jwt);
    }
}