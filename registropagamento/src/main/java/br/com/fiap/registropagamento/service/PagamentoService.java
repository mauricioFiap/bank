package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.Pagamento;

import java.util.List;

public interface PagamentoService {
    //List<Pagamento> findAll();
    Pagamento save(Pagamento pagamento);
   // void deleteById(String id);

    String processarPagamento(Pagamento pagamento, String token);

    List<Pagamento> findPagamentosByCpf(String chave);
}
