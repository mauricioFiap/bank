package br.com.fiap.geracartao.service.controller;


import br.com.fiap.geracartao.controller.CartaoController;
import br.com.fiap.geracartao.dto.CartaoConsultaRequest;
import br.com.fiap.geracartao.dto.CartaoRequest;
import br.com.fiap.geracartao.entity.Cartao;
import br.com.fiap.geracartao.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCartaoReturnsOkWhenSuccessful() throws Exception {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12345678901", "12/2022", "123", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);

        ResponseEntity<?> response = cartaoController.criarCartao(cartaoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartaoService, times(1)).criarCartao(cartao);
    }

    @Test
    void criarCartaoReturnsForbiddenWhenMaxCartoesReached() throws Exception {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12345678901", "12/2022", "123", 1000.0);
        doThrow(new Exception("Número máximo de cartões atingido")).when(cartaoService).criarCartao(any(Cartao.class));

        ResponseEntity<?> response = cartaoController.criarCartao(cartaoRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Número máximo de cartões atingido", response.getBody());
    }

    @Test
    void criarCartaoReturnsInternalServerErrorWhenOtherExceptionOccurs() throws Exception {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12345678901", "12/2022", "123", 1000.0);
        doThrow(new Exception("Some other error")).when(cartaoService).criarCartao(any(Cartao.class));

        ResponseEntity<?> response = cartaoController.criarCartao(cartaoRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro de negócio", response.getBody());
    }

    @Test
    void buscaCartaoPorNumeroEClienteCpfReturnsCartaoWhenFound() {
        CartaoConsultaRequest cartaoConsultaRequest = new CartaoConsultaRequest("123456789", "12345678901");
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12345678901", "12/2022", "123", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);
        when(cartaoService.findByNmeroAndClienteCpf(anyString(), anyString())).thenReturn(cartao);

        ResponseEntity<Cartao> response = cartaoController.buscaCartaoPorNumeroEClienteCpf(cartaoConsultaRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartao, response.getBody());
    }

    /*@Test
    void buscaCartaoPorNumeroEClienteCpfReturnsNotFoundWhenNotFound() {
        CartaoConsultaRequest cartaoConsultaRequest = new CartaoConsultaRequest("123456789", "12345678901");
        when(cartaoService.findByNmeroAndClienteCpf(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Cartao> response = cartaoController.buscaCartaoPorNumeroEClienteCpf(cartaoConsultaRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }*/
}