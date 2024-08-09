package br.com.fiap.registropagamento.dto;

import lombok.Data;

@Data
public class PagamentoRequest {
    private String cpf;
    private String numero;
    private String data_validade;
    private String cvv;
    private double valor;
    private String descricao;

}