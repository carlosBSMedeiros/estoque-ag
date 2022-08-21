package br.com.estoqueag.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.estoqueag.dtos.DTOGenerico;

public interface ServicoGenerico<T extends DTOGenerico, I> {

	public T incluir(T dto);
	
	public T alterar(T dto);
	
	public Page<T> listarTodos(Pageable pageable);
	
	public T listarPorId(I id);
	
	public void excluirPorId(I id);
	
}
