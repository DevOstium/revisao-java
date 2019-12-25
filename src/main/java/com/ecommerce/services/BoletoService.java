package com.ecommerce.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ecommerce.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamento(PagamentoComBoleto pagto, Date instanteDoPedido) {

		// Chamar o webservice aqui
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
		System.out.println("Data pagamento com Boleto : " + pagto.getDataVencimento());
	}

}
