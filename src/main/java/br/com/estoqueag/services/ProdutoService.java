package br.com.estoqueag.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.estoqueag.dtos.ProdutoDTO;
import br.com.estoqueag.entities.Produto;
import br.com.estoqueag.entities.enums.ProdutoSituacao;
import br.com.estoqueag.repositories.ProdutoRepository;
import br.com.estoqueag.utils.ValidadorProduto;

@Service
public class ProdutoService implements ServicoGenerico<ProdutoDTO, Long> {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ValidadorProduto validadorProduto;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProdutoDTO incluir(ProdutoDTO dto) {
		validadorProduto.validarProduto(dto, "inc");
		
		Produto p = mapper.map(dto, Produto.class);
		p = produtoRepository.save(p);
		dto = mapper.map(p,ProdutoDTO.class);
		return dto;
	}

	@Override
	public ProdutoDTO alterar(ProdutoDTO dto) {
		validadorProduto.validarProduto(dto, "alt");

		if(!produtoRepository.existsById(dto.getCodigo())){
			throw new EntityNotFoundException("Não existe produto com o código: " + dto.getCodigo());
		}
		
		Produto p = mapper.map(dto, Produto.class);
		p = produtoRepository.saveAndFlush(p);
		dto = mapper.map(p,ProdutoDTO.class);
		return dto;
	}

	@Override
	public Page<ProdutoDTO> listarTodos( Pageable pageable) {
		Page<Produto> result; 
		
		//TODO: Implementar busca com parâmetros dinâmicos
		result = produtoRepository.findAll(pageable);
		
		Page<ProdutoDTO> page = result.map(produto -> mapper.map(produto, ProdutoDTO.class));
		return page;
	}
	
	@Override
	public ProdutoDTO listarPorId(Long id) {
		Produto p = produtoRepository.findById(id).orElseThrow(() ->{
			throw new EntityNotFoundException("Não existe produto com o código: " + id);
		});
		ProdutoDTO dto = mapper.map(p,ProdutoDTO.class);
		return dto;
	}
	
	/**
	 * A exclusão de um produto não deve remove-lo do banco de dados,
	 * apenas atualizar sua situação para inativo
	 * */
	@Override
	public void excluirPorId(Long id) {
		Produto p = produtoRepository.findById(id).orElseThrow(() ->{
			throw new EntityNotFoundException("Não existe produto com o código: " + id);
		});
		p.setSituacao(ProdutoSituacao.INATIVO);
		produtoRepository.saveAndFlush(p);
	}
	


}
