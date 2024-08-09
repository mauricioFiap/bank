package br.com.fiap.autenticacao.config;

import br.com.fiap.autenticacao.entity.User;
import br.com.fiap.autenticacao.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UserServiceImpl userService;


    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            // Clean the database
            userService.deleteAll();

            User userAuth = new User("adj", "adj@1234");
            userService.saveUser(userAuth);

            User userClient = new User("client", "client");
            userService.saveUser(userClient);
        };
    }
}