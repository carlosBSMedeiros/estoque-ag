package br.com.estoqueag.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoqueag.dtos.ProdutoDTO;
import br.com.estoqueag.services.ProdutoService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/produto")
public class ProdutoController implements ControllerGenerico<ProdutoDTO, Long> {

	@Autowired
	ProdutoService produtoService;
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO incluir(@RequestBody ProdutoDTO dto) {
		ProdutoDTO novoDTO = produtoService.incluir(dto);
		return novoDTO;
	}
	
	@Override
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ProdutoDTO alterar(@RequestBody ProdutoDTO dto) {
		ProdutoDTO pautaAlterada = produtoService.alterar(dto);
		return pautaAlterada;
	}
	
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void excluir(@PathVariable Long id) {
		produtoService.excluirPorId(id);
	}
	
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<ProdutoDTO> listarTodos(@RequestParam Map<String,String> requestParams, Pageable pageable) {
		return produtoService.listarTodos(requestParams,pageable);
	}
	
	@Override
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoDTO listarPorId(@PathVariable Long id) {
		return produtoService.listarPorId(id);
	}

	
}
