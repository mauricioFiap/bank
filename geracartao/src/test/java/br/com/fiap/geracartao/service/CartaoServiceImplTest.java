package br.com.fiap.geracartao.service;


import br.com.fiap.geracartao.dto.CartaoRequest;
import br.com.fiap.geracartao.entity.Cartao;
import br.com.fiap.geracartao.entity.Cliente;
import br.com.fiap.geracartao.repository.CartaoRepository;
import br.com.fiap.geracartao.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCartaoSuccessfully() throws Exception {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12/2022", "123", "12345678901", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);
        when(cartaoRepository.countByCpf(cartao.getCpf())).thenReturn(Integer.valueOf("1"));
        when(clienteRepository.findById(cartao.getCpf())).thenReturn(Optional.of(new Cliente()));

        cartaoService.criarCartao(cartao);

        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void criarCartaoThrowsExceptionWhenMaxCartoesReached() {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12/2022", "123", "12345678901", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);
        when(cartaoRepository.countByCpf(cartao.getCpf())).thenReturn(2);

        Exception exception = assertThrows(Exception.class, () -> {
            cartaoService.criarCartao(cartao);
        });

        assertEquals("Número máximo de cartões atingido", exception.getMessage());
    }

    /*@Test
    void criarCartaoThrowsExceptionWhenClienteNotFound() {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12/2022", "123", "12345678901", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);
        when(cartaoRepository.countByCpf(cartao.getCpf())).thenReturn(1);
        when(clienteRepository.findById(cartao.getCpf())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cartaoService.criarCartao(cartao);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }*/

    @Test
    void findByNumeroAndClienteCpfReturnsCartao() {
        CartaoRequest cartaoRequest = new CartaoRequest("123456789", "12/2022", "123", "12345678901", 1000.0);
        Cartao cartao = Cartao.from(cartaoRequest);
        when(cartaoRepository.findByNumeroAndCpf("123456789", "12345678901")).thenReturn(cartao);

        Cartao result = cartaoService.findByNmeroAndClienteCpf("123456789", "12345678901");

        assertNotNull(result);
        assertEquals(cartao, result);
    }

    @Test
    void findByNumeroAndClienteCpfReturnsNullWhenNotFound() {
        when(cartaoRepository.findByNumeroAndCpf("123456789", "12345678901")).thenReturn(null);

        Cartao result = cartaoService.findByNmeroAndClienteCpf("123456789", "12345678901");

        assertNull(result);
    }

    @Test
    void deleteAllCartoes() {
        cartaoService.deleteAll();

        verify(cartaoRepository, times(1)).deleteAll();
    }
}