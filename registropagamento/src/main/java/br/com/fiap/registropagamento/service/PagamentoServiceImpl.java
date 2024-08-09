package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.Cartao;
import br.com.fiap.registropagamento.entity.Pagamento;
import br.com.fiap.registropagamento.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private final PagamentoRepository pagamentoRepository;

    @Autowired
    private final AutenticacaoService autenticacaoService;

    @Autowired
    private final CartaoServiceImpl cartaoService;


    public PagamentoServiceImpl(PagamentoRepository pagamentoRepository, AutenticacaoService autenticacaoService, CartaoServiceImpl cartaoService) {
        this.pagamentoRepository = pagamentoRepository;
        this.autenticacaoService = autenticacaoService;
        this.cartaoService = cartaoService;
    }

    /*@Override
    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();

    }*/

    @Override
    public Pagamento save(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

   /* @Override
    public void deleteById(String id) {
        pagamentoRepository.deleteById(id);
    }*/

    @Override
    public String processarPagamento(Pagamento pagamento, String token) {
        Cartao cartao = cartaoService.buscaCartaoPorNumeroEClienteCpf(pagamento.getNumero(), pagamento.getCpf());
        if (cartao == null) {
            cartao = cartaoService.buscaCartaoPorNumeroEClienteCpfByApiCartao(pagamento.getNumero(), pagamento.getCpf(), token);
            if (cartao != null) {

                cartaoService.salvarCartao(cartao);
            }

        }
        // Valida se o cartao existe para esse cliente
        if (cartao == null) {
            throw new RuntimeException("Erro de validação do cartão");
        }
        if (cartao.getPagamentos() == null) {
            cartao.setPagamentos(new ArrayList<>());
        }
        // Verificar limite do cartão
        if (limiteExcedido(cartao, pagamento)) {
            pagamento.setStatus("Reprovado: Limite do cartão estourado");
            cartao.getPagamentos().add(pagamento);
            cartaoService.salvarCartao(cartao);
            throw new RuntimeException("Limite do cartão estourado");
        }


        // Processar pagamento

        return processarPagamento(cartao, pagamento);
    }


    private boolean limiteExcedido(Cartao cartao, Pagamento pagamento) {

        // Verifica se o valor do pagamento é maior que o limite do cartão
        return pagamento.getValor() > cartao.getLimite();
    }


    private String processarPagamento(Cartao cartao, Pagamento pagamento) {
        if (!cartao.getCvv().equals(pagamento.getCvv())) {
            pagamento.setStatus("Reprovado: Erro de validação do cvv do cartão");
            cartao.getPagamentos().add(pagamento);
            cartaoService.salvarCartao(cartao);
            throw new RuntimeException("Erro de validação do cvv do cartão");
        }

        cartao.setLimite(cartao.getLimite() - pagamento.getValor());
        cartao.getPagamentos().add(pagamento);
        pagamento.setStatus("aprovado");
        cartaoService.salvarCartao(cartao);

        Random random = new Random();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);

    }

    @Override
    public List<Pagamento> findPagamentosByCpf(String chave) {
        // Implement the logic to find payments by client key
        return pagamentoRepository.findByCpf(chave);
    }
}