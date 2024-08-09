package br.com.fiap.registropagamento;

import br.com.fiap.registropagamento.util.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@Generated
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RegistropagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistropagamentoApplication.class, args);
	}

}
