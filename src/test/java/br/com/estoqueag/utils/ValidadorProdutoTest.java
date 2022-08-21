package br.com.estoqueag.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.estoqueag.ApplicationConfigTest;
import br.com.estoqueag.dtos.ProdutoDTO;

public class ValidadorProdutoTest extends ApplicationConfigTest{

	@Autowired
	ValidadorProduto validador;
	
	@Test
	@DisplayName("Deve disparar uma exception caso descricao seja nulo")
	public void validacaoProdutoDescricaoNulo() throws ParseException {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);
		Mockito.when(dto.getCodigo()).thenReturn(1l);
		Mockito.when(dto.getDescricao()).thenReturn(null);
		Mockito.when(dto.getDataFabricacao()).thenReturn(new SimpleDateFormat("dd/MM/yyyy").parse("08/06/2022"));
		Mockito.when(dto.getDataValidade()).thenReturn(Date.from(Instant.now()));

		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			validador.validarProduto(dto, "");
		});
		
		Assertions.assertEquals("Descricao não pode ser nula ou vazia;", exception.getMessage());

	}

	@Test
	@DisplayName("Deve disparar uma exception caso a data de validade seja menor que a de fabricação")
	public void validacaoDataValidadeMenorDataFabricacao() throws ParseException {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);
		Mockito.when(dto.getCodigo()).thenReturn(1l);
		Mockito.when(dto.getDescricao()).thenReturn("Descrição teste");
		Mockito.when(dto.getDataValidade()).thenReturn(new SimpleDateFormat("dd/MM/yyyy").parse("08/06/2022"));
		Mockito.when(dto.getDataFabricacao()).thenReturn(Date.from(Instant.now()));

		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			validador.validarProduto(dto, "");
		});
		
		Assertions.assertEquals("A data de fabriação deve ser anterior a data de validade do produto;", exception.getMessage());


	}

	@Test
	@DisplayName("Deve disparar uma exception caso codigo seja nulo na inclusão")
	public void validacaoProdutoNomeNuloInclusao() throws ParseException {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);
		Mockito.when(dto.getCodigo()).thenReturn(null);
		Mockito.when(dto.getDescricao()).thenReturn("Descrição teste");
		Mockito.when(dto.getDataFabricacao()).thenReturn(new SimpleDateFormat("dd/MM/yyyy").parse("08/06/2022"));
		Mockito.when(dto.getDataValidade()).thenReturn(Date.from(Instant.now()));

		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			validador.validarProduto(dto, "");
		});
		
		Assertions.assertEquals("Código não pode ser nulo ou vazio;", exception.getMessage());
	}

	
	
}
