package com.sac.backend.email;

import lombok.AllArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@AllArgsConstructor
public class EmailJob extends QuartzJobBean {
    private final EmailService emailService;

    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String email = jobDataMap.getString("email");
        String name = jobDataMap.getString("name");

        try {
            emailService.sendRating(email, name);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
