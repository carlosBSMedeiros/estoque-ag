package br.com.estoqueag.utils;

import br.com.estoqueag.dtos.ProdutoDTO;
import br.com.estoqueag.entities.enums.ProdutoSituacao;

public class ValidadorProduto {
	
	/**
	 * Responsavel por realizar validações gerais no ProdutoDTO recebido como parâmetro 
	 * 
	 * @see ProdutoDTO
	 * @param ProdutoDTO dto de produto
	 * @throws IllegalArgumentException
	 * */
	public void validarProduto(ProdutoDTO dto, String oper) {
		
		StringBuilder errosValidacao = new StringBuilder();
		
		if(!"inc".equals(oper) && dto.getCodigo() == null) {
			errosValidacao.append("Código não pode ser nulo ou vazio;");
		}
		
		if( dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
			errosValidacao.append("Descricao não pode ser nula ou vazia;");
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
