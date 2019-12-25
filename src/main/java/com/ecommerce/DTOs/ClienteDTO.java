package com.ecommerce.DTOs;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ecommerce.anotations.ClienteUpdate;
import com.ecommerce.domain.Cliente;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=2, max=200, message="O tamenho deve ser entre 2 e 200 caracteres ")
	private String nome;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email Invalido")
	private String email;

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
