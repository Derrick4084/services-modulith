package com.derocode.EcommApp.email.internals;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Profile("prod")
public class ProdJavaMailConfig {
    @Value("${dev.mail.host}")
    private String host;

    @Value("${dev.mail.port}")
    private int port;

    @Value("${dev.mail.username}")
    private String username;

    @Value("${dev.mail.password}")
    private String password;

    @Bean
    @Profile("prod")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "3000");
        props.put("mail.smtp.writetimeout", "5000");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.starttls.required", "false");
        props.put("mail.smtp.trust", "*");


        return mailSender;


    }
}
