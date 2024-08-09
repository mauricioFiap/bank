package br.com.fiap.registropagamento.repository;

import br.com.fiap.registropagamento.entity.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PagamentoRepository extends MongoRepository<Pagamento, String> {

    List<Pagamento> findByCpf(String cpf);
}
