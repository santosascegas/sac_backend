package com.sac.backend.services;

import com.sac.backend.helpers.EmailSender;
import com.sac.backend.DTO.FaleConoscoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaleConoscoService {

    @Autowired
    private EmailSender emailSender;

    public Boolean sendFaleConosco(FaleConoscoDTO faleConosco) {
        if (emailSender.enviarEmailFaleConosco(faleConosco)) return true;

        return false;
    }
}
