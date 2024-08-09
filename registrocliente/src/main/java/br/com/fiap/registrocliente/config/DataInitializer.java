package br.com.fiap.registrocliente.config;

import br.com.fiap.registrocliente.entity.User;
import br.com.fiap.registrocliente.service.UserServiceImpl;
import br.com.fiap.registrocliente.util.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Generated
@Configuration
public class DataInitializer {

    @Autowired
    private UserServiceImpl userService;


    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            // Clean the database
            userService.deleteAll();

            User userClient = new User("client", "client");
            userService.saveUser(userClient);
        };
    }
}