package com.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.Pedido;
import com.ecommerce.repositories.PedidoRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido findById(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionCustom(
				" Mensagem customizada de erro! Id : " + id + " - Tipo : " + Pedido.class.getName()));
	}

}
