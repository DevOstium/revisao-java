package com.ecommerce.anotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ecommerce.DTOs.ClienteDTO;
import com.ecommerce.domain.Cliente;
import com.ecommerce.repositories.ClienteRepository;
import com.ecommerce.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate clienteInsert) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer uriIdCliente = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		
		
		// Regra: Eu posso alterar o email do cliente.
		//       Mas não posso salvar o mesmo email em clientes distintos.
		//       Se o cliente de ID=1 tiver o email: teste@teste.com 
		//       O cliente de ID=2  NAO podera cadastrar-se com o email : teste@teste.com 
		if(cliente != null && !cliente.getId().equals(uriIdCliente) ) {
			list.add(new FieldMessage("email", "Email já existe validacao vindo da anotation ClienteUpdateValidator"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}

		return list.isEmpty();
	}
}
