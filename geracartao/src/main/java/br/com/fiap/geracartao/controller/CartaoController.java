package br.com.fiap.geracartao.controller;

import br.com.fiap.geracartao.dto.CartaoConsultaRequest;
import br.com.fiap.geracartao.dto.CartaoRequest;
import br.com.fiap.geracartao.entity.Cartao;
import br.com.fiap.geracartao.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartao")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<?> criarCartao(@RequestBody CartaoRequest cartaoRequest) {
        try {
            //String cpf = cartao.getClienteCpf(); // Assuming CPF is the username in JWT
            Cartao cartao = Cartao.from(cartaoRequest);
            cartaoService.criarCartao(cartao);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e.getMessage().equals("Número máximo de cartões atingido")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Número máximo de cartões atingido");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro de negócio");
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<Cartao> buscaCartaoPorNumeroEClienteCpf(@RequestBody CartaoConsultaRequest cartaoConsultaRequest) {
        Cartao cartao = cartaoService.findByNmeroAndClienteCpf(cartaoConsultaRequest.getNumero(), cartaoConsultaRequest.getCpf());
        return ResponseEntity.ok(cartao);
    }

    /*@PostMapping("/debitar")
    ResponseEntity<Object> debitarCartao(@RequestBody PagamentoRequest pagamentoRequest) {
        Cartao cartao = cartaoService.findByNmeroAndClienteCpf(pagamentoRequest.getNumeroCartao(), pagamentoRequest.getCpf());
        if (cartao != null) {
            cartaoService.debitar(cartao, pagamentoRequest.getValor());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
    }*/
}