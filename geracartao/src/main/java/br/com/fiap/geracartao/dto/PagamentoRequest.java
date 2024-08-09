package br.com.fiap.geracartao.dto;

import lombok.Data;

@Data
public class PagamentoRequest {
    private String numeroCartao;
    private String cpf;
    private String nomeTitular;
    private String validade;
    private String codigoSeguranca;
    private double valor;


}
