package br.com.fiap.autenticacao.controller;

import br.com.fiap.autenticacao.entity.User;
import br.com.fiap.autenticacao.model.AuthenticationRequest;
import br.com.fiap.autenticacao.model.TokenResponse;
import br.com.fiap.autenticacao.service.UserServiceImpl;
import br.com.fiap.autenticacao.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/autenticacao")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        User user = userService.authenticate(authenticationRequest.getUsuario(), authenticationRequest.getSenha());
        if (user != null) {
            final String jwt = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new TokenResponse(jwt));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}