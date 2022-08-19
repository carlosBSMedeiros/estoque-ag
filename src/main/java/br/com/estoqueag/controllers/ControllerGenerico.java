package br.com.estoqueag.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.estoqueag.dtos.DTOGenerico;
import br.com.estoqueag.dtos.ProdutoDTO;

public interface ControllerGenerico<T extends DTOGenerico, I> {

	ProdutoDTO listarPorId(I id);

	ProdutoDTO incluir(T dto);

	ProdutoDTO alterar(T dto);

	void excluir(I id);

	Page<ProdutoDTO> listarTodos(Map<String, String> requestParams, Pageable pageable);

}