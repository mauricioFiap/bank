package br.com.fiap.geracartao.dto;

import lombok.Data;

@Data
public class CartaoConsultaRequest {
    private String cpf;
    private String limitr;
    private String numero;
    private String dataValidade;
    private String cvv;

    public CartaoConsultaRequest(String cpf, String numero) {
        this.cpf = cpf;
        this.numero = numero;

    }
}