package br.com.fiap.geracartao.config;

import br.com.fiap.geracartao.dto.CartaoRequest;
import br.com.fiap.geracartao.entity.Cartao;
import br.com.fiap.geracartao.entity.User;
import br.com.fiap.geracartao.service.CartaoServiceImpl;
import br.com.fiap.geracartao.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartaoServiceImpl cartaoService;


    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            // Clean the database
            userService.deleteAll();
            cartaoService.deleteAll();

            // Create a user for the client

            User userAdmin = new User("adj", "adj@1234");
            userService.saveUser(userAdmin);

            User userClient = new User("client", "client");
            userService.saveUser(userClient);

            // create Cartao
            CartaoRequest cartaoRequest = new CartaoRequest();
            cartaoRequest.setNumero("123456789");
            cartaoRequest.setData_validade("12/2022");
            cartaoRequest.setCvv("123");
            cartaoRequest.setLimite(1000.0);
            cartaoRequest.setCpf("12345678901");

            Cartao cartao = Cartao.from(cartaoRequest);
            cartaoService.criarCartao(cartao);

        };
    }
}