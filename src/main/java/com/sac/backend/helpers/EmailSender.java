package com.sac.backend.helpers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private String nome;
    private String emailUsuario;
    private String assunto;
    private String conteudo;

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmailConfirmacao() {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg);

            helper.setTo("test_test@gmail.com");

            helper.setSubject("Testando email de confirmacao");
            helper.setText("<h1>Testando H1 pra ver se envia HTML</h1>", true);

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emailFaleconosco(){
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);

            helper.setTo("renato@hotmail.com");

            helper.setSubject(assunto);
            helper.setText("Email de " + nome + "\nemail: " + emailUsuario + "\n\n" + conteudo);

            javaMailSender.send(msg);
            }
         catch (Exception e) {
             e.printStackTrace();
        }
    }
}
