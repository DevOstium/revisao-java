package com.ecommerce.anotations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.DTOs.ClienteNewDTO;
import com.ecommerce.domain.Cliente;
import com.ecommerce.domain.enums.TipoCliente;
import com.ecommerce.repositories.ClienteRepository;
import com.ecommerce.resources.exceptions.FieldMessage;
import com.ecommerce.util.ValidaCpfOuCnpj;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert clienteInsert) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if( objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !ValidaCpfOuCnpj.isValidCPF(objDto.getCpfOuCnpj()) )  {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}

		if( objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !ValidaCpfOuCnpj.isValidCPF(objDto.getCpfOuCnpj()) )  {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		
		if(cliente != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
