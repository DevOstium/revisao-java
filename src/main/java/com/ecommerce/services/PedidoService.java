package com.ecommerce.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.ItemPedido;
import com.ecommerce.domain.PagamentoComBoleto;
import com.ecommerce.domain.Pedido;
import com.ecommerce.domain.Produto;
import com.ecommerce.domain.enums.EstadoPagamento;
import com.ecommerce.email.EmailService;
import com.ecommerce.repositories.ItemPedidoRepository;
import com.ecommerce.repositories.PagamentoRepository;
import com.ecommerce.repositories.PedidoRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService; 
	
	@Autowired
	private EmailService emailService;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptionCustom(
				" Mensagem customizada de erro! Id : " + id + " - Tipo : " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.findById(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		
		// Se o meu pagmaneto for do tipo pagamento com Boleto
		// Lembrar que pagamento é uma classe abstrata e PagamentoComBoleto extends Pagamento  
		if(obj.getPagamento() instanceof PagamentoComBoleto ) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			
			//Add data de vencimento do Pagamento
			boletoService.preencherPagamento(pagto, obj.getInstante());
		}

		// Salvando o Pedido
		obj = repo.save(obj);
		
		// Salvar o Pagamento
		pagamentoRepository.save(obj.getPagamento());
		
		// Salvar os itens no Banco de Dados
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);

			
			Produto produto = produtoService.findById(ip.getProduto().getId());
			ip.setProduto(produto);
			// Pegar o preço que está cadastrado no Produto
			//Double precoProduto = produtoService.findById(ip.getProduto().getId()).getPreco();
			//ip.setPreco(precoProduto); 
			
			ip.setPreco(ip.getProduto().getPreco());

			// Associando ItemPedido com o Pedido que eu estou inserindo
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		    //System.out.println(" Inserindo o Pedido ");
			//System.out.println(obj);
		
			//emailService.sendOrderConfirmationEmail(obj);
			emailService.sendOrderConfirmationHtmlEmail(obj);
			
		return obj;
	}

}



















