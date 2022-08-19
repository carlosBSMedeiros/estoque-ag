package br.com.estoqueag.services;

import java.util.Map;

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

@Service
public class ProdutoService implements ServicoGenerico<ProdutoDTO, Long> {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProdutoDTO incluir(ProdutoDTO dto) {
		validarProduto(dto, "inc");
		
		Produto p = mapper.map(dto, Produto.class);
		p = produtoRepository.save(p);
		dto = mapper.map(p,ProdutoDTO.class);
		return dto;
	}

	@Override
	public ProdutoDTO alterar(ProdutoDTO dto) {
		validarProduto(dto);

		if(!produtoRepository.existsById(dto.getCodigo())){
			throw new EntityNotFoundException("Não existe produto com o código: " + dto.getCodigo());
		}
		
		Produto p = mapper.map(dto, Produto.class);
		p = produtoRepository.saveAndFlush(p);
		dto = mapper.map(p,ProdutoDTO.class);
		return dto;
	}

	@Override
	public Page<ProdutoDTO> listarTodos(Map<String,String> requestParams, Pageable pageable) {
		Page<Produto> result; 
		
		if(requestParams.isEmpty()) {
			result = produtoRepository.findAll(pageable);
		} else {
			result = produtoRepository.findAll(pageable);
		}
		
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
	
	private void validarProduto(ProdutoDTO dto) {
		this.validarProduto(dto, "");
	}
	/**
	 * Responsavel por realizar validações gerais no ProdutoDTO recebido como parâmetro 
	 * 
	 * @see ProdutoDTO
	 * @param ProdutoDTO dto de produto
	 * @throws IllegalArgumentException
	 * */
	private void validarProduto(ProdutoDTO dto, String oper) {
		
		StringBuilder errosValidacao = new StringBuilder();
		
		if(!"inc".equals(oper) && dto.getCodigo() == null) {
			errosValidacao.append("Código não pode ser nulo ou vazio");
		}
		
		if( dto.getDescricao().trim().isEmpty()) {
			errosValidacao.append("Descricao não pode ser nula ou vazia");
		}
		
		if(!(dto.getDataFabricacao().compareTo(dto.getDataValidade()) < 0)) {
			errosValidacao.append("A data de fabriação deve ser anterior a data de validade do produto;");
		}
		
		if(!errosValidacao.isEmpty()) {
			throw new IllegalArgumentException(errosValidacao.toString());
		}
		
		//garante que o produto está ativado
		dto.setSituacao(ProdutoSituacao.ATIVO);
	}

}
