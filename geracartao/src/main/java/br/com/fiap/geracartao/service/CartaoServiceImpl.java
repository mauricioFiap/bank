package br.com.fiap.geracartao.service;

import br.com.fiap.geracartao.entity.Cartao;
import br.com.fiap.geracartao.repository.CartaoRepository;
import br.com.fiap.geracartao.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartaoServiceImpl implements CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void criarCartao(Cartao cartao) throws Exception {
        if (cartaoRepository.countByCpf(cartao.getCpf()) >= 2) {
            throw new Exception("Número máximo de cartões atingido");
        }
        cartaoRepository.save(cartao);
    }

    @Override
    public Cartao findByNmeroAndClienteCpf(String numero, String cpf) {
        return cartaoRepository.findByNumeroAndCpf(numero, cpf);
    }


    @Override
    public void deleteAll() {
        cartaoRepository.deleteAll();
    }


}
