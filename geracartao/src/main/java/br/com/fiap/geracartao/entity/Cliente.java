package br.com.fiap.geracartao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "clientes")
public class Cliente {
    @Id
    private String cpf;
    private String nome;
    private List<Cartao> cartoes;

}