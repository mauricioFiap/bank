package br.com.fiap.registropagamento.repository;

import br.com.fiap.registropagamento.entity.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartaoRepository extends MongoRepository<Cartao, String> {
    Cartao findByNumeroAndCpf(String numero, String cpf);

    List<Cartao> findByCpf(String cpf);
}