package br.com.fiap.geracartao.repository;

import br.com.fiap.geracartao.entity.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartaoRepository extends MongoRepository<Cartao, String> {
    int countByCpf(String cpf);

    Cartao findByNumeroAndCpf(String numero, String cpf);
}