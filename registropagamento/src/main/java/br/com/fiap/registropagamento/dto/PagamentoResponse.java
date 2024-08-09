package br.com.fiap.registropagamento.dto;

import br.com.fiap.registropagamento.entity.Pagamento;
import lombok.Builder;

@Builder
public class PagamentoResponse {
    public String valor;
    public String descricao;
    public String status;
    public String metodo_pagamento;

    public static PagamentoResponse from(Pagamento pagamento) {
        return PagamentoResponse.builder()
                .valor(pagamento.getValor().toString())
                .descricao(pagamento.getDescricao())
                .status(pagamento.getStatus())
                .metodo_pagamento("cartao_credito")
                .build();
    }
}
