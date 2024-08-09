package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.Cartao;

import java.util.List;

public interface CartaoService {
    public Cartao buscaCartaoPorNumeroEClienteCpfByApiCartao(String numero, String cpf, String token);
    public Cartao buscaCartaoPorNumeroEClienteCpf(String numero, String cpf);

    public void salvarCartao(Cartao cartao);

    public List<Cartao> buscaCartoesPorCpf(String cpf);
}
