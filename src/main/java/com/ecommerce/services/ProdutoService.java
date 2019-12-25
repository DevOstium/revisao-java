package com.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.Categoria;
import com.ecommerce.domain.Produto;
import com.ecommerce.repositories.CategoriaRepository;
import com.ecommerce.repositories.ProdutoRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@Service
public class ProdutoService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository repo;

	public Produto findById(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionCustom(
				" Mensagem customizada de erro! Id : " + id + " - Tipo : " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction ){
		
		PageRequest     pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias  = categoriaRepository.findAllById(ids);

		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
}






