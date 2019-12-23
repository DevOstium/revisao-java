package com.ecommerce.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.Categoria;
import com.ecommerce.domain.Cidade;
import com.ecommerce.domain.Cliente;
import com.ecommerce.domain.Endereco;
import com.ecommerce.domain.Estado;
import com.ecommerce.domain.ItemPedido;
import com.ecommerce.domain.Pagamento;
import com.ecommerce.domain.PagamentoComBoleto;
import com.ecommerce.domain.PagamentoComCartao;
import com.ecommerce.domain.Pedido;
import com.ecommerce.domain.Produto;
import com.ecommerce.domain.enums.EstadoPagamento;
import com.ecommerce.domain.enums.TipoCliente;
import com.ecommerce.repositories.CategoriaRepository;
import com.ecommerce.repositories.CidadeRepository;
import com.ecommerce.repositories.ClienteRepository;
import com.ecommerce.repositories.EnderecoRepository;
import com.ecommerce.repositories.EstadoRepository;
import com.ecommerce.repositories.ItemPedidoRepository;
import com.ecommerce.repositories.PagamentoRepository;
import com.ecommerce.repositories.PedidoRepository;
import com.ecommerce.repositories.ProdutoRepository;

@Service
@Configuration
public class PopulaDBH2 {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Bean
	@Transactional
	public void DB() throws ParseException {
	
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 800.00);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo",  est2);
		Cidade c3 = new Cidade(null, "Campinas",   est2);
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Fagner lira", "fagnerlira@rmail.com", "2233442222", TipoCliente.PESSOAFISICA);
   		        cli1.getTelefones().addAll(Arrays.asList("1177889988", "1177886655"));
		
		Endereco e1 = new Endereco(null, "Rua Centro", "22", "casa", "Centro", "13222889", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Vila Matos", "4444", "AP", "Parque Marqcos", "33442121", cli1, c2);
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
				//Associando o endereco ao cliente
				cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
		//Criand o pedido
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 07:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("12/10/2019 12:30"), cli1, e2);
		
		//Criando o pagamento
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/12/2017 23:33"), null);
	
		//Associando o pedido ao pagamento
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		
		//Associando os pedidos ao Cliente
		cli1.getPedido().addAll(Arrays.asList(ped1, ped2));
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		
		//Item do Pedido
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 2, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}
}

















