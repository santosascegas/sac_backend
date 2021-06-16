package com.sac.backend.helpers;

import javax.mail.internet.MimeMessage;

import com.sac.backend.models.Agendamento;
import com.sac.backend.DTO.FaleConoscoDTO;

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

            String[] data = agendamentoModel.getData().getData().split("&");

            String dia = data[0];
            String horario = data[1];

            String body = "<h2>Santos às Cegas</h2>"
                        + "<h4>Olá " + agendamentoModel.getNomeUsuario() + "!! Estamos entrando em contato para informar que seu agendamento foi realizado com sucesso!</h4>"
                        + "<p><strong>Data do trajeto:</strong> " + dia + "</p>"
                        + "<p><strong>Horário:</strong> " + horario + "</p>"
                        + "<br />";
            
            if (agendamentoModel.getAtestado() == 1) 
                body = body 
                    + "<p>Por favor, não se esqueça de apresentar seu atestado médico no dia do seu trajeto.</p>"
                    + "<br />";

            body = body 
                + "<p>Temos algumas dicas para melhorar ainda mais a sua experiência durante o trajeto!</p>"
                + "<br />"
                + "<p>O ponto de encontro:</p>"
                + "<p>Canal 1 (Senador Pinheiro Machado) com a praia, ao lado dos quiosques.</p>"
                + "<br />"
                + "<p>Em caso de chuva no dia do trajeto, observaremos a situação da chuva, entraremos em contato com você para remarcar o trajeto ou não. No caso de chuva ao longo do trajeto, faremos uma parada em um local alternativo.</p>"
                + "<br />"
                + "<p>Para essa e outras informações visite nossa página de dúvidas frequentes em nosso site:</p>"
                + "<a href='http://localhost:3000/faq'>Dúvidas Frequentes</a>"
                + "<br />"
                + "<br />"
                + "<p>Atenciosamente,</p>"
                + "<strong>Equipe Santos às cegas</strong>";
        

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

            String[] data = agendamentoModel.getData().getData().split("&");
            
            String dia = data[0];
            String horario = data[1];


            String body = "<h2>Santos às Cegas</h2>"
                        + "<h4>Um novo agendamento foi realizado!</h4>"
                        + "<p><strong>Nome:</strong> " + agendamentoModel.getNomeUsuario() + "</p>"
                        + "<p><strong>Telefone:</strong> " + agendamentoModel.getTelefone() + "</p>"
                        + "<p><strong>Documento:</strong> " + agendamentoModel.getDocumento() + "</p>"
                        + "<p><strong>Email:</strong> " + agendamentoModel.getEmailUsuario() + "</p>"
                        + "<p><strong>Dia:</strong> " + dia + "</p>"
                        + "<p><strong>Horário:</strong> " + horario + "</p>"
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

    public Boolean enviarEmailFaleConosco(FaleConoscoDTO faleConosco){
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setFrom(faleConosco.getEmail());
            helper.setTo(emailAdmin);

            String body = "<h3>Email de Contato Recebido do Site Santos às Cegas</h3>"
                        + "<p><strong>Nome:</strong> " + faleConosco.getNome() + "</p>"
                        + "<p><strong>Email:</strong> " + faleConosco.getEmail() + "</p>"
                        + "<p><strong>Telefone:</strong> " + faleConosco.getTelefone() + "</p>"
                        + "<p><strong>Assunto:</strong> " + faleConosco.getAssunto() + "</p>"
                        + "<p><strong>Mensagem:</strong> " + faleConosco.getMensagem() + "</p>";
                        
            helper.setSubject("Fale Conosco Santos as Cegas - " + faleConosco.getAssunto());
            helper.setText(body, true);

            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
