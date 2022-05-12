package com.sac.backend.contact;

import com.sac.backend.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class ContactService {

    private final EmailService emailService;

    public Boolean sendContact(Contact contact) {
        try {
            emailService.sendContact(contact);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
