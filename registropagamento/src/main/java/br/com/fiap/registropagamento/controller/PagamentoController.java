package br.com.fiap.registropagamento.controller;

import br.com.fiap.registropagamento.dto.PagamentoRequest;
import br.com.fiap.registropagamento.dto.PagamentoResponse;
import br.com.fiap.registropagamento.entity.Cartao;
import br.com.fiap.registropagamento.entity.Pagamento;
import br.com.fiap.registropagamento.service.CartaoService;
import br.com.fiap.registropagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<?> processarPagamento(@RequestBody PagamentoRequest pagamentoRequest, @RequestHeader("Authorization") String token) {
        try {
            Pagamento pagamento = Pagamento.from(pagamentoRequest);
            String chavePagamento = pagamentoService.processarPagamento(pagamento, token);
            return ResponseEntity.ok().body(Collections.singletonMap("chave_pagamento", chavePagamento));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("validação do cartão")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else if (e.getMessage().contains("Limite do cartão estourado")) {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping("/cliente/{chave}")
    public ResponseEntity<List<PagamentoResponse>> listarPagamentosPorCliente(@PathVariable String chave) {
        try {
            List<Cartao> cartoes = cartaoService.buscaCartoesPorCpf(chave);
            List<Pagamento> pagamentos = cartoes.stream().map(Cartao::getPagamentos).flatMap(List::stream).toList();

            List<PagamentoResponse> response = pagamentos.stream().map(PagamentoResponse::from).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("autorização")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }


}