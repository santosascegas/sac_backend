package com.sac.backend.email;

import com.sac.backend.appointment.Appointment;
import com.sac.backend.contact.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final Environment env;
    private final Scheduler scheduler;

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

    public void sendRating(String email, String name) throws MessagingException {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", name);
            properties.put("link", "http://www.gogole.com/");

            context.setVariables(properties);

            helper.setFrom(admin_email);
            helper.setTo(email);
            helper.setSubject("Relate sua experiência - Santos às Cegas");
            String html = templateEngine.process("clientRatingEmail.html", context);
            helper.setText(html, true);

            log.info("Sending email to: {} with html body: {}", email, html);
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

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void scheduleEmail(Appointment appointment) {
        try {
            Date appointment_date = appointment.getAgenda().getDate();
            appointment_date.setTime(appointment_date.getTime() + TimeUnit.HOURS.toMillis(4));
            ZonedDateTime dateTime = ZonedDateTime.of(convertToLocalDateTime(appointment_date), ZoneId.of("America/Sao_Paulo"));
            if (dateTime.isBefore(ZonedDateTime.now())) {
                throw new RuntimeException("dateTime precisa está mais tarde que a hora atual!");
            }

            JobDetail jobDetail = buildJobDetail(appointment);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    private JobDetail buildJobDetail(Appointment appointment) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", appointment.getEmail());
        jobDataMap.put("name", appointment.getName());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
