package br.com.fiap.registropagamento.entity;

import br.com.fiap.registropagamento.dto.PagamentoRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
//@Document(collection = "pagamentos")
public class Pagamento {
    @Id
    private String id;
    private String cpf;
    private String numero;
    private String dataValidade;
    private String cvv;
    private Double valor;
    private String descricao;
    private String status;

    public static Pagamento from(PagamentoRequest pagamentoRequest) {
        return Pagamento.builder()
                .cpf(pagamentoRequest.getCpf())
                .numero(pagamentoRequest.getNumero())
                .dataValidade(pagamentoRequest.getData_validade())
                .cvv(pagamentoRequest.getCvv())
                .valor(pagamentoRequest.getValor())
                .descricao(pagamentoRequest.getDescricao())
                .build();
    }

}