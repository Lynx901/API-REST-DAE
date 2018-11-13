/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dae.dae1819.pojos;

import com.dae.dae1819.interfaces.EmailService;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author dml y jfaf
 */
@Component
public class EmailServiceImpl implements EmailService {
    
    @Autowired
    public JavaMailSenderImpl emailSender;
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.gmail.com");
        emailSender.setPort(587);

        emailSender.setUsername("desarrolloaplicacionesweb181@gmail.com");
        emailSender.setPassword("daedaedae");

        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return emailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}