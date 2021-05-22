package com.sac.backend.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.sac.backend.models.AgendamentoModel;
import com.sac.backend.services.AgendamentoService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List getAllAgendamentos() {
        return agendamentoService.getAllAgendamentos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Optional<AgendamentoModel> getAgendamento(@PathVariable Long id) {
        return agendamentoService.getAgendamento(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    public void addAgendamento(@RequestBody AgendamentoModel agendamento) {
        agendamentoService.addAgendamento(agendamento);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public void updateAgendamento(@RequestBody AgendamentoModel agendamento, @PathVariable String id) {
        agendamentoService.updateAgendamento(id, agendamento);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteAgendamento(@PathVariable Long id) {
        agendamentoService.deleteAgendamento(id);
    }

}
