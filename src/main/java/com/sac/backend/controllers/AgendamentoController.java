package com.sac.backend.controllers;

import java.util.List;
import java.util.Optional;

import com.sac.backend.models.Agendamento;
import com.sac.backend.services.AgendamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoService.getAllAgendamentos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Optional<Agendamento> getAgendamento(@PathVariable Long id) {
        return agendamentoService.getAgendamento(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Agendamento> addAgendamento(@RequestBody Agendamento agendamento) {
        return ResponseEntity.ok(agendamentoService.addAgendamento(agendamento));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public void updateAgendamento(@RequestBody Agendamento agendamento, @PathVariable String id) {
        agendamentoService.updateAgendamento(id, agendamento);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteAgendamento(@PathVariable Long id) {
        agendamentoService.deleteAgendamento(id);
    }

}
