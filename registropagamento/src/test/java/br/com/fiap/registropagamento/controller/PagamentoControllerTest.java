
package br.com.fiap.registropagamento.controller;

import br.com.fiap.registropagamento.dto.PagamentoRequest;

import static br.com.fiap.registropagamento.dto.PagamentoResponse.from;

import br.com.fiap.registropagamento.dto.PagamentoResponse;
import br.com.fiap.registropagamento.entity.Cartao;

import static br.com.fiap.registropagamento.entity.Pagamento.from;

import br.com.fiap.registropagamento.entity.Pagamento;
import br.com.fiap.registropagamento.service.CartaoService;
import br.com.fiap.registropagamento.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagamentoControllerTest {

    @Mock
    private PagamentoService pagamentoService;

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processarPagamento_ValidRequest_ReturnsOk() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        //Pagamento pagamento = new Pagamento();
        String chavePagamento = "123456";

        when(pagamentoService.processarPagamento(any(Pagamento.class), anyString())).thenReturn(chavePagamento);

        ResponseEntity<?> response = pagamentoController.processarPagamento(pagamentoRequest, "token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("chave_pagamento", chavePagamento), response.getBody());
    }

    @Test
    void processarPagamento_CartaoValidacaoError_ReturnsUnauthorized() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();

        when(pagamentoService.processarPagamento(any(Pagamento.class), anyString())).thenThrow(new RuntimeException("Erro de validação do cartão"));

        ResponseEntity<?> response = pagamentoController.processarPagamento(pagamentoRequest, "token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void processarPagamento_LimiteEstouradoError_ReturnsPaymentRequired() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();

        when(pagamentoService.processarPagamento(any(Pagamento.class), anyString())).thenThrow(new RuntimeException("Limite do cartão estourado"));

        ResponseEntity<?> response = pagamentoController.processarPagamento(pagamentoRequest, "token");

        assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode());
    }

    @Test
    void processarPagamento_OtherError_ReturnsInternalServerError() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();

        when(pagamentoService.processarPagamento(any(Pagamento.class), anyString())).thenThrow(new RuntimeException("Other error"));

        ResponseEntity<?> response = pagamentoController.processarPagamento(pagamentoRequest, "token");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void listarPagamentosPorCliente_ValidChave_ReturnsOk() {
        String chave = "12345678900";
        Cartao cartaoMock = mock(Cartao.class);
        PagamentoRequest pagamentoRequest = new PagamentoRequest();

        Pagamento pagamento = Pagamento.from(pagamentoRequest);
        PagamentoResponse pagamentoResponse = PagamentoResponse.from(pagamento);
        List<Pagamento> pagamentos = List.of(pagamento);
        List<PagamentoResponse> pagamentoResponses = List.of(pagamentoResponse);

        when(cartaoService.buscaCartoesPorCpf(chave)).thenReturn(List.of(cartaoMock));
        when(cartaoMock.getPagamentos()).thenReturn(pagamentos);
       // when(PagamentoResponse.from(any(Pagamento.class))).thenReturn(pagamentoResponse);

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(pagamentoResponses, response.getBody());
    }

    @Test
    void listarPagamentosPorCliente_AutorizacaoError_ReturnsUnauthorized() {
        String chave = "12345678900";

        when(cartaoService.buscaCartoesPorCpf(chave)).thenThrow(new RuntimeException("Erro de autorização"));

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void listarPagamentosPorCliente_OtherError_ReturnsInternalServerError() {
        String chave = "12345678900";

        when(cartaoService.buscaCartoesPorCpf(chave)).thenThrow(new RuntimeException("Other error"));

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

/*@Test
    void listarPagamentosPorCliente_ValidChave_ReturnsOk() {
        String chave = "12345678900";
        List<Cartao> cartoes = List.of(new Cartao());
        List<Pagamento> pagamentos = List.of(Pagamento.from(new PagamentoRequest()));
        List<PagamentoResponse> pagamentoResponses = List.of(PagamentoResponse.from(Pagamento.from(new PagamentoRequest())));

        when(cartaoService.buscaCartoesPorCpf(chave)).thenReturn(cartoes);
        when(cartoes.get(0).getPagamentos()).thenReturn(pagamentos);
        when(PagamentoResponse.from(any(Pagamento.class))).thenReturn(pagamentoResponses.get(0));

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentoResponses, response.getBody());
    }*//*


    @Test
    void listarPagamentosPorCliente_ValidChave_ReturnsOk() {


        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setCpf("12345678900");
        pagamentoRequest.setValor(100.0);
        pagamentoRequest.setDescricao("Pagamento de teste");
        pagamentoRequest.setNumero("12345678900");
        pagamentoRequest.setData_validade("12/2022");
        pagamentoRequest.setCvv("123");


        Pagamento pagamento = Pagamento.from(pagamentoRequest);
        String chave = "12345678900";
        Cartao cartaoMock = mock(Cartao.class);
        //Pagamento pagamento = Pagamento.builder().build();
        PagamentoResponse pagamentoResponse = PagamentoResponse.from(pagamento);
        List<Pagamento> pagamentos = List.of(pagamento);
        List<PagamentoResponse> pagamentoResponses = List.of(pagamentoResponse);

        when(cartaoService.buscaCartoesPorCpf(chave)).thenReturn(List.of(cartaoMock));
        when(cartaoMock.getPagamentos()).thenReturn(pagamentos);
       // when(PagamentoResponse.from(pagamento)).thenReturn(pagamentoResponse);

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(pagamentoResponses, response.getBody());
    }

    @Test
    void listarPagamentosPorCliente_AutorizacaoError_ReturnsUnauthorized() {
        String chave = "12345678900";

        when(cartaoService.buscaCartoesPorCpf(chave)).thenThrow(new RuntimeException("Erro de autorização"));

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void listarPagamentosPorCliente_OtherError_ReturnsInternalServerError() {
        String chave = "12345678900";

        when(cartaoService.buscaCartoesPorCpf(chave)).thenThrow(new RuntimeException("Other error"));

        ResponseEntity<List<PagamentoResponse>> response = pagamentoController.listarPagamentosPorCliente(chave);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


}*/
