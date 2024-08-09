package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.Cartao;
import br.com.fiap.registropagamento.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private RestTemplate restTemplate;


    private CartaoServiceImpl cartaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartaoService = spy(new CartaoServiceImpl(cartaoRepository));
        cartaoService.setRestTemplate(restTemplate);

    }

    @Test
    void buscaCartaoPorNumeroEClienteCpfByApiCartao_Success() {
        Cartao expectedCartao = new Cartao();
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Cartao.class)))
                .thenReturn(new ResponseEntity<>(expectedCartao, HttpStatus.OK));

        Cartao result = cartaoService.buscaCartaoPorNumeroEClienteCpfByApiCartao("1234567890123456", "12345678900", "token");

        assertNotNull(result);
        assertEquals(expectedCartao, result);
    }

    @Test
    void buscaCartaoPorNumeroEClienteCpfByApiCartao_HttpClientErrorException() {
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Cartao.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        Cartao result = cartaoService.buscaCartaoPorNumeroEClienteCpfByApiCartao("1234567890123456", "12345678900", "token");

        assertNull(result);
    }

    @Test
    void buscaCartaoPorNumeroEClienteCpfByApiCartao_Exception() {
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Cartao.class)))
                .thenThrow(new RuntimeException());

        Cartao result = cartaoService.buscaCartaoPorNumeroEClienteCpfByApiCartao("1234567890123456", "12345678900", "token");

        assertNull(result);
    }

    @Test
    void buscaCartaoPorNumeroEClienteCpf_Success() {
        Cartao expectedCartao = new Cartao();
        when(cartaoRepository.findByNumeroAndCpf("1234567890123456", "12345678900"))
                .thenReturn(expectedCartao);

        Cartao result = cartaoService.buscaCartaoPorNumeroEClienteCpf("1234567890123456", "12345678900");

        assertNotNull(result);
        assertEquals(expectedCartao, result);
    }

    @Test
    void salvarCartao_Success() {
        Cartao cartao = new Cartao();
        cartaoService.salvarCartao(cartao);
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void buscaCartoesPorCpf_Success() {
        List<Cartao> expectedCartoes = List.of(new Cartao());
        when(cartaoRepository.findByCpf("12345678900")).thenReturn(expectedCartoes);

        List<Cartao> result = cartaoService.buscaCartoesPorCpf("12345678900");

        assertNotNull(result);
        assertEquals(expectedCartoes, result);
    }

    @Test
    void salvarCartao_ValidCartao_Success() {
        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setCpf("12345678900");
        cartao.setLimite(1000.0);

        doNothing().when(cartaoService).salvarCartao(cartao);

        cartaoService.salvarCartao(cartao);

        verify(cartaoService, times(1)).salvarCartao(cartao);
    }

    @Test
    void salvarCartao_NullCartao_ThrowsException() {
        doThrow(new IllegalArgumentException("Cartão não pode ser nulo")).when(cartaoService).salvarCartao(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cartaoService.salvarCartao(null));
        assertEquals("Cartão não pode ser nulo", exception.getMessage());
    }

    @Test
    void salvarCartao_CartaoComDadosInvalidos_ThrowsException() {
        Cartao cartao = new Cartao();
        cartao.setNumero("123");
        cartao.setCpf("123");

        doThrow(new RuntimeException("Dados do cartão inválidos")).when(cartaoService).salvarCartao(cartao);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartaoService.salvarCartao(cartao));
        assertEquals("Dados do cartão inválidos", exception.getMessage());
    }
}
