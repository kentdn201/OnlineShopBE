package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class OnlineShopApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApiApplication.class, args);
	}
}
