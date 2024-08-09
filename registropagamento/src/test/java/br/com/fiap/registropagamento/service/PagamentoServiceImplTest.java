package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.dto.PagamentoRequest;
import br.com.fiap.registropagamento.entity.Cartao;
import br.com.fiap.registropagamento.entity.Pagamento;
import br.com.fiap.registropagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class PagamentoServiceImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private AutenticacaoService autenticacaoService;

    @Mock
    private CartaoServiceImpl cartaoService;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processarPagamento_CartaoNaoEncontrado() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setNumero("1234567890123456");
        pagamentoRequest.setCpf("12345678900");

        Pagamento pagamento = Pagamento.from(pagamentoRequest);

        when(cartaoService.buscaCartaoPorNumeroEClienteCpf(anyString(), anyString())).thenReturn(null);
        when(cartaoService.buscaCartaoPorNumeroEClienteCpfByApiCartao(anyString(), anyString(), anyString())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.processarPagamento(pagamento, "token"));
        assertEquals("Erro de validação do cartão", exception.getMessage());
    }

    @Test
    void processarPagamento_LimiteExcedido() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setNumero("1234567890123456");
        pagamentoRequest.setCpf("12345678900");
        pagamentoRequest.setValor(2000.0);
        Pagamento pagamento = Pagamento.from(pagamentoRequest);

        Cartao cartao = new Cartao();
        cartao.setLimite(1000.0);
        cartao.setPagamentos(new ArrayList<>());

        when(cartaoService.buscaCartaoPorNumeroEClienteCpf(anyString(), anyString())).thenReturn(cartao);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.processarPagamento(pagamento, "token"));
        assertEquals("Limite do cartão estourado", exception.getMessage());
    }

    @Test
    void processarPagamento_CvvInvalido() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setNumero("1234567890123456");
        pagamentoRequest.setCpf("12345678900");
        pagamentoRequest.setValor(500.0);
        pagamentoRequest.setCvv("123");
        Pagamento pagamento = Pagamento.from(pagamentoRequest);

        Cartao cartao = new Cartao();
        cartao.setLimite(1000.0);
        cartao.setCvv("456");
        cartao.setPagamentos(new ArrayList<>());

        when(cartaoService.buscaCartaoPorNumeroEClienteCpf(anyString(), anyString())).thenReturn(cartao);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pagamentoService.processarPagamento(pagamento, "token"));
        assertEquals("Erro de validação do cvv do cartão", exception.getMessage());
    }

    @Test
    void processarPagamento_Success() {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setNumero("1234567890123456");
        pagamentoRequest.setCpf("12345678900");
        pagamentoRequest.setValor(500.0);
        pagamentoRequest.setCvv("123");
        Pagamento pagamento = Pagamento.from(pagamentoRequest);
        Cartao cartao = new Cartao();
        cartao.setLimite(1000.0);
        cartao.setCvv("123");
        cartao.setPagamentos(new ArrayList<>());

        when(cartaoService.buscaCartaoPorNumeroEClienteCpf(anyString(), anyString())).thenReturn(cartao);

        String result = pagamentoService.processarPagamento(pagamento, "token");

        assertNotNull(result);
        assertEquals("aprovado", pagamento.getStatus());
        assertTrue(result.matches("\\d{6}"));
    }

    @Test
    void findPagamentosByCpf_Success() {
        List<Pagamento> expectedPagamentos = List.of(Pagamento.from(new PagamentoRequest()));
        when(pagamentoRepository.findByCpf("12345678900")).thenReturn(expectedPagamentos);

        List<Pagamento> result = pagamentoService.findPagamentosByCpf("12345678900");

        assertNotNull(result);
        assertEquals(expectedPagamentos, result);
    }
}
