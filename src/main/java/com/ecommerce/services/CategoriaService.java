package com.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ecommerce.DTOs.CategoriaDTO;
import com.ecommerce.domain.Categoria;
import com.ecommerce.repositories.CategoriaRepository;
import com.ecommerce.services.exceptions.DataIntegrityViolationExceptionCustom;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionCustom(
				" Mensagem customizada de erro! Id : " + id + " - Tipo : " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
			Categoria newObj = findById(obj.getId()); 
			updateDATA( newObj,  obj);
		return repo.save(obj);
	}

	private void updateDATA(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
	public void delete(Categoria obj) {
		findById(obj.getId());
		
		try {
			repo.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationExceptionCustom("Não é possivel Deletar esse Recurso  ID : " + obj.getId());
		}
	}

	public List<CategoriaDTO> findAll() {
		List<CategoriaDTO> dto = repo.findAll().stream().map( categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return dto;
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}







