package br.com.estoqueag.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.estoqueag.ApplicationConfigTest;
import br.com.estoqueag.dtos.ProdutoDTO;
import br.com.estoqueag.entities.Produto;
import br.com.estoqueag.repositories.ProdutoRepository;
import br.com.estoqueag.utils.ValidadorProduto;

public class ProdutoServiceTest extends ApplicationConfigTest {

	@MockBean
	private ProdutoRepository produtoRepository;

	@MockBean
	private ModelMapper mapper;

	@MockBean
	ValidadorProduto validadorProduto;

	@Autowired
	ProdutoService service;

	@Test
	@DisplayName("Deve incluir um Produto sem erros")
	public void incluirSemErros() {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);

		Mockito.when(mapper.map(ArgumentMatchers.any(ProdutoDTO.class), ArgumentMatchers.eq(Produto.class)))
				.thenReturn(new Produto());

		Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(new Produto());

		Mockito.when(mapper.map(ArgumentMatchers.any(Produto.class), ArgumentMatchers.eq(ProdutoDTO.class)))
				.thenReturn(new ProdutoDTO());

		service.incluir(dto);

		Mockito.verify(produtoRepository, Mockito.times(1)).save(ArgumentMatchers.any(Produto.class));
	}

	@Test
	@DisplayName("Deve alterar um Produto sem erros")
	public void alterarSemErros() {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);

		Mockito.when(produtoRepository.existsById(ArgumentMatchers.any(Long.class))).thenReturn(true);

		Mockito.when(mapper.map(ArgumentMatchers.any(ProdutoDTO.class), ArgumentMatchers.eq(Produto.class)))
				.thenReturn(new Produto());

		Mockito.when(produtoRepository.saveAndFlush(ArgumentMatchers.any(Produto.class))).thenReturn(new Produto());

		Mockito.when(mapper.map(ArgumentMatchers.any(Produto.class), ArgumentMatchers.eq(ProdutoDTO.class)))
				.thenReturn(new ProdutoDTO());

		service.alterar(dto);

		Mockito.verify(produtoRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(Produto.class));
	}

	@Test
	@DisplayName("Deve disparar uma exception ao tentar alterar um produto que não existe")
	public void alterarComErroProdutoInexistente() {
		ProdutoDTO dto = Mockito.mock(ProdutoDTO.class);

		Mockito.when(produtoRepository.existsById(ArgumentMatchers.any(Long.class))).thenReturn(false);

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.alterar(dto);
		});

		Mockito.verify(produtoRepository, Mockito.times(1)).existsById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	@DisplayName("Deve desativar um produto corretamente")
	public void delecaoLogicaProdutoSemErros() {
		Long codigo = 1l;
		Produto p = new Produto();
		p.setCodigo(codigo);
		Mockito.when(produtoRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(p));
		service.excluirPorId(codigo);

		Mockito.verify(produtoRepository, Mockito.times(1)).findById(ArgumentMatchers.any(Long.class));
		Mockito.verify(produtoRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(Produto.class));

	}
	
	@Test
	@DisplayName("Deve disparar um erro ao tentar desativar um produto inexistente")
	public void delecaoLogicaComErroProdutoInexistente() {
		Long codigo = 1l;
		Mockito.when(produtoRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.excluirPorId(codigo);
		});

		Mockito.verify(produtoRepository, Mockito.times(1)).findById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	@DisplayName("Recuperar produto pelo código")
	public void listarProdutoPorCodigo() {
		Long codigo = 1l;
		Produto p = new Produto();
		p.setCodigo(codigo);

		ProdutoDTO dto = new ProdutoDTO();
		dto.setCodigo(p.getCodigo());

		Mockito.when(produtoRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(p));
		Mockito.when(mapper.map(ArgumentMatchers.any(Produto.class), ArgumentMatchers.eq(ProdutoDTO.class)))
				.thenReturn(dto);

		ProdutoDTO dtoResult = service.listarPorId(codigo);

		Mockito.verify(produtoRepository, Mockito.times(1)).findById(ArgumentMatchers.any(Long.class));
		Assertions.assertEquals(codigo, dtoResult.getCodigo());
	}

	@Test
	@DisplayName("Disparar Exception ao não encontrar produto com id recebido como parâmetro")
	public void listarProdutoPorIdComErroIdInexistente() {
		Long codigo = 1l;
		Mockito.when(produtoRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.listarPorId(codigo);
		});

		Mockito.verify(produtoRepository, Mockito.times(1)).findById(ArgumentMatchers.any(Long.class));
	}

}
