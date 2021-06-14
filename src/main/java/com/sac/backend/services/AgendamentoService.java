package com.sac.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.models.Agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EmailSender emailSender;

    public List<Agendamento> getAllAgendamentos() {

        List<Agendamento> agendamentos = new ArrayList<>();
        agendamentoRepository.findAll().forEach(agendamentos::add);

        return agendamentos;
    }

    public Optional<Agendamento> getAgendamento(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento addAgendamento(Agendamento agendamento) {
        emailSender.enviarEmailConfirmacao(agendamento);
        emailSender.enviarEmailConfirmacaoAdmin(agendamento);
        return agendamentoRepository.save(agendamento);
    }

    public void updateAgendamento(String id, Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
    }

    public void deleteAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
