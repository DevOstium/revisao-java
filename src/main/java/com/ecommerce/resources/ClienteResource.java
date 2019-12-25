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

import com.ecommerce.DTOs.ClienteDTO;
import com.ecommerce.DTOs.ClienteNewDTO;
import com.ecommerce.domain.Cliente;
import com.ecommerce.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
		Cliente obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@PostMapping
	// @RequestBody convert o objeto json para o objeto Cateria.
	// O eu envio como paramento um objeto json
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO obj) {
		
			Cliente clienteConvert = service.fromDTO(obj);
		
			Cliente categoriaResult = service.insert(clienteConvert);
		
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoriaResult.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO obj, @PathVariable Integer id){
		    obj.setId(id);
			Cliente categoriaConvert = service.fromDTO(obj);
			service.update(categoriaConvert);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete( @PathVariable Integer id){
			service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Usar :  http://localhost:8080/categorias/pagination?page=1&linesPerPage=5&orderBy=nome=&direction=ASC
	//Ex: GET : http://localhost:8080/categorias/pagination?linesPerPage=3
	@GetMapping("/pagination")
	public ResponseEntity<Page<ClienteDTO>> findPage ( @RequestParam(value="page",         defaultValue="0")     Integer  page
			                                            ,@RequestParam(value="linesPerPage", defaultValue="24")    Integer  linesPerPage   // 24 é multiplo de 1,2,3,4 
			                                            ,@RequestParam(value="orderBy",      defaultValue="nome")  String   orderBy        // campo = nome
			                                            ,@RequestParam(value="direction",    defaultValue="ASC")   String   direction)     //  ASC ou DESC
	                                                  {
			Page<Cliente> pageCliente = service.findPage(page, linesPerPage, orderBy, direction);
			// Como o Page já java 8 complaice não preciso usar o stream() e nem desse collect(Collectors.toList())
			Page<ClienteDTO> listDTO = pageCliente.map(categoria -> new ClienteDTO(categoria));
		return ResponseEntity.ok().body(listDTO);
	}

}
