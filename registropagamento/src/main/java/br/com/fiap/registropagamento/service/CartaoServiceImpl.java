package br.com.fiap.registropagamento.service;

import br.com.fiap.registropagamento.entity.Cartao;
import br.com.fiap.registropagamento.dto.CartaoConsultaRequest;
import br.com.fiap.registropagamento.repository.CartaoRepository;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CartaoServiceImpl implements CartaoService {

    @Setter
    RestTemplate restTemplate = new RestTemplate();
    private final CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Cartao buscaCartaoPorNumeroEClienteCpfByApiCartao(String numero, String cpf, String token) {
        String url = "http://api-geracartao:8083/api/cartao/validar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        CartaoConsultaRequest requestPayload = new CartaoConsultaRequest();
        requestPayload.setNumero(numero);
        requestPayload.setCpf(cpf);

        HttpEntity<CartaoConsultaRequest> request = new HttpEntity<>(requestPayload, headers);

        try {

            ResponseEntity<Cartao> response = restTemplate.postForEntity(url, request, Cartao.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Erro de cliente HTTP: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Cartao buscaCartaoPorNumeroEClienteCpf(String numero, String cpf) {
        return cartaoRepository.findByNumeroAndCpf(numero, cpf);
    }

    @Override
    public void salvarCartao(Cartao cartao) {
        cartaoRepository.save(cartao);
    }

    @Override
    public List<Cartao> buscaCartoesPorCpf(String cpf) {
        return cartaoRepository.findByCpf(cpf);
    }
}