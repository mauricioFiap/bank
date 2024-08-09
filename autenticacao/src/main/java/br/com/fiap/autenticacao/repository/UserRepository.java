package br.com.fiap.autenticacao.repository;

import br.com.fiap.autenticacao.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{username:?0}")
    User findByUsername(String username);
}