package br.com.fiap.registropagamento.dto;

import lombok.Data;

@Data
public class CartaoConsultaRequest {
    private String numero;
    private String cpf;

}