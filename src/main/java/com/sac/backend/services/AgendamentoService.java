package com.sac.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.Agendamento;
import com.sac.backend.models.Datas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService implements ServiceInterface<Agendamento> {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private DatasService datasService;
   
    @Override
    public Agendamento create(Agendamento agendamento) {
        emailSender.enviarEmailConfirmacao(agendamento);
        emailSender.enviarEmailConfirmacaoAdmin(agendamento);

        // Pega a data usada no agendamento, atualiza seu status para 1 (utilizada) e atualiza no banco
        Datas dataAgendamento = agendamento.getData();
        dataAgendamento.setStatus(1);
        datasService.update(dataAgendamento);

        return agendamentoRepository.save(agendamento);
    }

    @Override
    public Optional<Agendamento> findById(Long id) {
        Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
        return agendamento;
    }

    @Override
    public List<Agendamento> findAll() {
        List<Agendamento> agendamentos = new ArrayList<>();
        agendamentoRepository.findAll().forEach(agendamentos::add);

        return agendamentos;
    }

    @Override
    public boolean update(Agendamento obj) {
        if (agendamentoRepository.existsById(obj.getId())) {
            agendamentoRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (agendamentoRepository.existsById(id)) {
            agendamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
