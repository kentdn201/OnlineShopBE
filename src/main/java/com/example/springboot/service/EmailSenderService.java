package com.example.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendEmail(String to,
                          String subject,
                          String text) throws MessagingException {
//      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//      MailSender mailSender = (MailSender) context.getBean("mailSender");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//      SimpleMailMessage message = new SimpleMailMessage();
        helper.setFrom("kentdn200103@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        javaMailSender.send(mimeMessage);
        System.out.println("Sending text done!");
    }
}
