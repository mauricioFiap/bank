package br.com.fiap.registrocliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RegistroclienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroclienteApplication.class, args);
	}

}
