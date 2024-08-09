package br.com.fiap.authenticator.controller;

import br.com.fiap.authenticator.request.AuthenticationRequest;
import br.com.fiap.authenticator.response.AuthenticationResponse;
import br.com.fiap.authenticator.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/autenticacao")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        AuthenticationResponse response = authService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }
}