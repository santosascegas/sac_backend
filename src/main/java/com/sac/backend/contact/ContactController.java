package com.sac.backend.contact;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fale-conosco")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(value = "/")
    public ResponseEntity<?> sendContact(@RequestBody Contact contact) {
        return contactService.sendContact(contact)
                ? ResponseEntity.status(200).build()
                : ResponseEntity.status(500).build();
    }
}
