package com.sac.backend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.models.AgendamentoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EmailSender emailSender;

    public List getAllAgendamentos() {

        List agendamentos = new ArrayList<>();
        agendamentoRepository.findAll().forEach(agendamentos::add);

        return agendamentos;
    }

    public Optional<AgendamentoModel> getAgendamento(Long id) {
        return agendamentoRepository.findById(id);
    }

    public void addAgendamento(AgendamentoModel agendamento) {
        emailSender.enviarEmailConfirmacao(agendamento);
        emailSender.enviarEmailConfirmacaoAdmin(agendamento);
        agendamentoRepository.save(agendamento);
    }

    public void updateAgendamento(String id, AgendamentoModel agendamento) {
        agendamentoRepository.save(agendamento);
    }

    public void deleteAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
