package br.com.fiap.geracartao.entity;

import br.com.fiap.geracartao.dto.CartaoRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "cartoes")

public class Cartao {
    @Id
    private String id;
    private String numero;
    private String dataValidade;
    private String cvv;
    private double limite;
    private String cpf;

    public static Cartao from(CartaoRequest cartaoRequest) {
        return Cartao.builder()
                .numero(cartaoRequest.getNumero())
                .dataValidade(cartaoRequest.getData_validade())
                .cvv(cartaoRequest.getCvv())
                .limite(cartaoRequest.getLimite())
                .cpf(cartaoRequest.getCpf())
                .build();
    }
}