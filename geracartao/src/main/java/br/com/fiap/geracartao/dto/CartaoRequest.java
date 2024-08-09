package br.com.fiap.geracartao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartaoRequest {
    private String cpf;
    private String numero;
    private String data_validade;
    private String cvv;
    private double limite;

    public CartaoRequest() {
        
    }
}
