package com.ecommerce.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ecommerce.email.EmailService;
import com.ecommerce.email.SmtpEmailService;
import com.ecommerce.util.PopulaDBH2;

@Configuration
@Profile("dev")
public class DevConfig {

	/*
	 * Com essa configuracao eu vou ativar todos os beans que estiverem nessa classe quando o 
	 * spring.profiles.active=test dentro do arquivo application.properties
	 */
	
	@Autowired
	private PopulaDBH2 populaDBH2;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
			if(!"create".equals(strategy)) {
				return false;
			}
			populaDBH2.DB();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
}
