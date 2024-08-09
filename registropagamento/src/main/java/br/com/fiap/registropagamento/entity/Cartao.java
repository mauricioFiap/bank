package br.com.fiap.registropagamento.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "cartoes")
public class Cartao {
    @Id
    private String id;
    private String numero;
    private String dataValidade;
    private String cvv;
    private double limite;
    private String cpf;
    private List<Pagamento> pagamentos;





}