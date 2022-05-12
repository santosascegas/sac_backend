package com.sac.backend.email;

import com.sac.backend.appointment.Appointment;
import com.sac.backend.contact.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final Environment env;

    @Value("${spring.mail.username}")
    private String admin_email;

    public void sendClientAppointmentEmail(Appointment appointment) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", appointment.getName());

            Date date = appointment.getAgenda().getDate();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.setTime(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            properties.put("appointmentDay", dateFormat.format(calendar.getTime()));
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            properties.put("appointmentHour", hourFormat.format(calendar.getTime()));

            if (appointment.getDoctorsNote() != null) {
                properties.put("doctorsNote", true);
            }

            context.setVariables(properties);

            helper.setFrom(admin_email);
            helper.setTo(appointment.getEmail());
            helper.setSubject(String.format("Confirmação agendamento - %s", appointment.getIdDocument()));
            String html = templateEngine.process("clientAppointmentConfirmationEmail.html", context);
            helper.setText(html, true);

            log.info("Sending email to: {} with html body: {}", appointment.getEmail(), html);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAdminAppointmentEmail(Appointment appointment) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", appointment.getName());

            Date date = appointment.getAgenda().getDate();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.setTime(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            properties.put("appointmentDay", dateFormat.format(calendar.getTime()));
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            properties.put("appointmentHour", hourFormat.format(calendar.getTime()));

            if (appointment.getDoctorsNote() != null) {
                properties.put("doctorsNote", true);
            }

            context.setVariables(properties);

            helper.setFrom(admin_email);
            helper.setTo(appointment.getEmail());
            helper.setSubject(String.format("Confirmação agendamento - %s", appointment.getIdDocument()));
            String html = templateEngine.process("adminAppointmentConfirmationEmail.html", context);
            helper.setText(html, true);

            log.info("Sending email to: {} with html body: {}", appointment.getEmail(), html);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendContact(Contact contact) throws MessagingException {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", contact.getName());
            properties.put("email", contact.getEmail());
            properties.put("phone", contact.getPhone());
            properties.put("subject", contact.getSubject());
            properties.put("message", contact.getMessage());

            context.setVariables(properties);

            helper.setFrom(admin_email);
            helper.setTo(contact.getEmail());
            helper.setSubject(String.format("Fale Conosco Santos as Cegas - %s", contact.getSubject()));
            String html = templateEngine.process("clientContact.html", context);
            helper.setText(html, true);

            log.info("Sending email to: {} with html body: {}", contact.getEmail(), html);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
