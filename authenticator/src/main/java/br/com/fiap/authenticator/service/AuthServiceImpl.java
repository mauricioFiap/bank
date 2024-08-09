package br.com.fiap.authenticator.service;

import br.com.fiap.authenticator.request.AuthenticationRequest;
import br.com.fiap.authenticator.response.AuthenticationResponse;
import br.com.fiap.authenticator.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl extends AuthService {
   /* @Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Verificar se o usuário existe e a senha está correta
        // (implementar a lógica de verificação com seu banco de dados)
        /*if (!"user".equals(request.getUsername()) ||
                !passwordEncoder.matches(request.getPassword(), "password")) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }*/

        // Gerar o token JWT
        String jwt = jwtUtils.generateToken(request.getUsername());

        return new AuthenticationResponse(jwt);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implementar a lógica para buscar o usuário no banco de dados
        // (substituir o retorno null pelo usuário do banco de dados)
        if ("abb".equals(username)) {
            return new org.springframework.security.core.userdetails.User("mramos", "102030", null);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
