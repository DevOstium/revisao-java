package com.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ecommerce.DTOs.ClienteDTO;
import com.ecommerce.DTOs.ClienteNewDTO;
import com.ecommerce.domain.Cidade;
import com.ecommerce.domain.Cliente;
import com.ecommerce.domain.Endereco;
import com.ecommerce.domain.enums.TipoCliente;
import com.ecommerce.repositories.ClienteRepository;
import com.ecommerce.repositories.EnderecoRepository;
import com.ecommerce.services.exceptions.DataIntegrityViolationExceptionCustom;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionCustom(
				" Mensagem customizada de erro! Id : " + id + " - Tipo : " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
			
			obj.setId(null);
		
			obj = repo.save(obj);
			
			enderecoRepository.saveAll(obj.getEnderecos());
		
		 return obj;
	}

	public Cliente update(Cliente obj) {
		
		Cliente newObj = findById(obj.getId());
		
		updateDATA(newObj, obj);
		
		return repo.save(newObj);
	}

	private void updateDATA(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		findById(id);
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationExceptionCustom("Não é possivel Deletar por que há entidades relacionadas " );
		}
	}

	public List<ClienteDTO> findAll() {
		List<ClienteDTO> dto = repo.findAll().stream().map( categoria -> new ClienteDTO(categoria)).collect(Collectors.toList());
		return dto;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO dto) {
		// Somente para eu lembrar que precisa de implementacao
		//throw new UnsupportedOperationException(); 
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente  cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()) );
		Cidade   cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);

			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
	
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}

}



















