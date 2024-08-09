package br.com.fiap.autenticacao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
    private String usuario;
    private String senha;

}
