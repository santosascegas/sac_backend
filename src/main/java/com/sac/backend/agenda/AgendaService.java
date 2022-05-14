package com.sac.backend.agenda;

import com.sac.backend.interfaces.ServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendaService implements ServiceInterface<Agenda> {

    private final AgendaRepository agendaRepository;

    @Override
    public Agenda create(Agenda obj) {
        agendaRepository.save(obj);
        return obj;
    }

    @Override
    public Optional<Agenda> findById(Long id) {
        return agendaRepository.findById(id);
    }

    @Override
    public List<Agenda> findAll() {
        return new ArrayList<Agenda>(agendaRepository.findAll());
    }

    @Override
    public boolean update(Agenda obj) {
        if (agendaRepository.existsById(obj.getId())) {
            agendaRepository.save(obj);
            return true;
        }
        return false;
    }

    public boolean makeAgendaUnavailable(Long id) {
        if (agendaRepository.existsById(id)) {
            Agenda agenda = agendaRepository.getById(id);
            agenda.setIsAvailable(false);
            agendaRepository.save(agenda);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Agenda> listOnlyAvailable() {
        return agendaRepository.listOnlyAvailable();
    }
}
