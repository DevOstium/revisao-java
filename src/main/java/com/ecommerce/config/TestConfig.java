package com.ecommerce.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ecommerce.email.EmailService;
import com.ecommerce.email.MockEmailService;
import com.ecommerce.util.PopulaDBH2;

@Configuration
@Profile("test")
public class TestConfig {

	/*
	 * Com essa configuracao eu vou ativar todos os beans que estiverem nessa classe quando o 
	 * spring.profiles.active=test dentro do arquivo application.properties
	 */
	
	@Autowired
	private PopulaDBH2 populaDBH2;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		populaDBH2.DB();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
