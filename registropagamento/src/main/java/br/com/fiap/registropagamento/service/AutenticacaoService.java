package br.com.fiap.registropagamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AutenticacaoService {

    public String obterToken(String username, String password) {
        String url = "http://localhost:8080/api/autenticacao";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("adj", "adj@1234");

        HttpEntity<String> request = new HttpEntity<>(String.format("{\"clientId\":\"%s\", \"password\":\"%s\"}", username, password), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException.Forbidden e) {
            System.out.println("Acesso proibido: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (HttpClientErrorException e) {
            System.out.println("Erro de cliente HTTP: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}