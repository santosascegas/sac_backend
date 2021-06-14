package com.sac.backend.helpers;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.sac.backend.models.Agendamento;
import com.sac.backend.models.FaleConosco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.admin}")
    private String emailAdmin;

    public void enviarEmailConfirmacao(Agendamento agendamentoModel) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setFrom(emailAdmin);
            helper.setTo(agendamentoModel.getEmailUsuario());

            String[] data = agendamentoModel.getDt().split("&");

            String dia = data[0];
            String horario = data[1];


            String body = "<h2>Santos às Cegas</h2>"
                        + "<h4>" + agendamentoModel.getNomeUsuario() + ", o seu agendamento está confirmado!</h4>"
                        + "<p><strong>Dia:</strong> " + dia + "</p>"
                        + "<p><strong>Horario:</strong> " + horario + "</p>"
                        + "<br />";
            
            if (agendamentoModel.getAtestado() == 1) 
                body = body 
                    + "<p>Por favor, apresentar atestado médico no dia de seu trajeto.</p>"
                    + "<br />";

            body = body 
                + "<h4>Muito obrigado e qualquer dúvida entre em contato conosco!</h4>";
        

            helper.setSubject("Confirmação agendamento - " + agendamentoModel.getDocumento());
            helper.setText(body, true);
            

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarEmailConfirmacaoAdmin(Agendamento agendamentoModel) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setFrom(agendamentoModel.getEmailUsuario());
            helper.setTo(emailAdmin);

            String[] data = agendamentoModel.getDt().split("&");
            
            String dia = data[0];
            String horario = data[1];


            String body = "<h2>Santos às Cegas</h2>"
                        + "<h4>Um novo agendamento foi realizado!</h4>"
                        + "<p><strong>Nome:</strong> " + agendamentoModel.getNomeUsuario() + "</p>"
                        + "<p><strong>Telefone:</strong> " + agendamentoModel.getTelefone() + "</p>"
                        + "<p><strong>Documento:</strong> " + agendamentoModel.getDocumento() + "</p>"
                        + "<p><strong>Email:</strong> " + agendamentoModel.getEmailUsuario() + "</p>"
                        + "<p><strong>Dia:</strong> " + dia + "</p>"
                        + "<p><strong>Horario:</strong> " + horario + "</p>"
                        + "<br />";
            
            if (agendamentoModel.getAtestado() == 1) 
                body = body 
                    + "<p> Deverá apresentar atestado médico.</p>";
        

            helper.setSubject("Novo agendamento - " + agendamentoModel.getNomeUsuario());
            helper.setText(body, true);
            

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean enviarEmailFaleConosco(FaleConosco faleConosco){
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setFrom(faleConosco.getEmail());
            helper.setTo(emailAdmin);

            String body = "<h3>Email de Contato Recebido do Site Santos às Cegas</h3>"
                        + "<p><strong>Nome:</strong> " + faleConosco.getNome() + "</p>"
                        + "<p><strong>Telefone:</strong> " + faleConosco.getTelefone() + "</p>"
                        + "<p><strong>Assunto:</strong> " + faleConosco.getAssunto() + "</p>"
                        + "<p><strong>Mensagem:</strong> " + faleConosco.getMensagem() + "</p>";
                        
            helper.setSubject("Fale Conosco Santos as Cegas - " + faleConosco.getAssunto());
            helper.setText(body, true);
            // helper.addInline("logo", new File("~/Documents/sac_frontend/public/images/logo-principal.png"));

            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
