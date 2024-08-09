package br.com.fiap.geracartao.repository;

import br.com.fiap.geracartao.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
}