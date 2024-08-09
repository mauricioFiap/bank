package br.com.fiap.registrocliente.request;

import lombok.Data;

@Data
public class ClienteRequest {

    private String id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
}
