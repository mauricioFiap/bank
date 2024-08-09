package br.com.fiap.registrocliente.repository;

import br.com.fiap.registrocliente.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, Long> {
}