package com.ecommerce.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.DTOs.CategoriaDTO;
import com.ecommerce.domain.Categoria;
import com.ecommerce.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
		Categoria obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@PostMapping
	// @RequestBody convert o objeto json para o objeto Cateria.
	// O eu envio como paramento um objeto json
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO obj) {
		
			Categoria categoriaConvert = service.fromDTO(obj);
		
			Categoria categoriaResult = service.insert(categoriaConvert);
		
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoriaResult.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO obj, @PathVariable Integer id){
		
		    obj.setId(id);
			Categoria categoriaConvert = service.fromDTO(obj);
		
			service.update(categoriaConvert);
			
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete( @RequestBody Categoria obj, @PathVariable Integer id){
			
			obj.setId(id);
			
			service.delete(obj);
			
		return ResponseEntity.noContent().build();
	}
	
	//Usar :  http://localhost:8080/categorias/pagination?page=1&linesPerPage=5&orderBy=nome=&direction=ASC
	//Ex: GET : http://localhost:8080/categorias/pagination?linesPerPage=3
	@GetMapping("/pagination")
	public ResponseEntity<Page<CategoriaDTO>> findPage ( @RequestParam(value="page",         defaultValue="0")     Integer  page
			                                            ,@RequestParam(value="linesPerPage", defaultValue="24")    Integer  linesPerPage   // 24 é multiplo de 1,2,3,4 
			                                            ,@RequestParam(value="orderBy",      defaultValue="nome")  String   orderBy        // campo = nome
			                                            ,@RequestParam(value="direction",    defaultValue="ASC")   String   direction)     //  ASC ou DESC
	                                                  {
			Page<Categoria> pageCategoria = service.findPage(page, linesPerPage, orderBy, direction);
		
			// Como o Page já java 8 complaice não preciso usar o stream() e nem desse collect(Collectors.toList())
			Page<CategoriaDTO> listDTO = pageCategoria.map(categoria -> new CategoriaDTO(categoria));
			
		return ResponseEntity.ok().body(listDTO);
	}
	
}
