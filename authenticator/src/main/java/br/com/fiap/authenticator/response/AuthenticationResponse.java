package br.com.fiap.authenticator.response;

import lombok.Data;

@Data
public class AuthenticationResponse  {
    private String token;

    public AuthenticationResponse(String jwt) {
        this.token = jwt;
    }

}
