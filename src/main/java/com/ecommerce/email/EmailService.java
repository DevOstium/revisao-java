package com.ecommerce.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domain.Pedido;

public interface EmailService {

	//Texto plano
		// esses esta  @Override
		void sendOrderConfirmationEmail(Pedido obj);
		// Para enviar um email com texto plano -- Vou usar esse
		void sendEmail(SimpleMailMessage msg);

	
	
	// Usar o HTML
	
		// esses esta  @Override
		void sendOrderConfirmationHtmlEmail(Pedido obj);
		void sendHtmlEmail(MimeMessage msg);
}
