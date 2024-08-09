package br.com.fiap.registrocliente.entity;

import br.com.fiap.registrocliente.request.ClienteRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "clientes")
@Builder
public class Cliente {

    @Id
    private String id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    //bild Cliente from ClienteRequest using Builder pattern
    public static Cliente from(ClienteRequest clienteRequest) {
        return Cliente.builder()
                .cpf(clienteRequest.getCpf())
                .nome(clienteRequest.getNome())
                .email(clienteRequest.getEmail())
                .telefone(clienteRequest.getTelefone())
                .rua(clienteRequest.getRua())
                .cidade(clienteRequest.getCidade())
                .estado(clienteRequest.getEstado())
                .cep(clienteRequest.getCep())
                .pais(clienteRequest.getPais())
                .build();
    }

}