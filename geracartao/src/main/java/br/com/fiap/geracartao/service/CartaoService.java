package br.com.fiap.geracartao.service;

import br.com.fiap.geracartao.entity.Cartao;

import java.util.List;

public interface CartaoService {
    public void criarCartao(Cartao cartao) throws Exception;

    Cartao findByNmeroAndClienteCpf(String numero, String cpf);

    void deleteAll();
}
