package com.sac.backend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.models.AgendamentoModel;
import com.sac.backend.models.FaleConoscoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaleConoscoService {

    @Autowired
    private EmailSender emailSender;

    public Boolean sendFaleConosco(FaleConoscoModel faleConosco) {
        if (emailSender.enviarEmailFaleConosco(faleConosco)) return true;

        return false;
    }
}
