package br.com.estoqueag;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.estoqueag.utils.ValidadorProduto;

@SpringBootApplication
public class EstoqueAgApplication {

	@Bean
	public ValidadorProduto validadorProduto() {
		return new ValidadorProduto();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EstoqueAgApplication.class, args);
	}

}
