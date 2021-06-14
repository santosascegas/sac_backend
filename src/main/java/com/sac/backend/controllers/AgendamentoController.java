package com.sac.backend.controllers;

import java.util.List;
import java.util.Optional;

import com.sac.backend.interfaces.Control;
import com.sac.backend.models.Agendamento;
import com.sac.backend.services.AgendamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController implements Control<Agendamento> {

    @Autowired
    private AgendamentoService agendamentoService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Agendamento>> getAll() {
        return ResponseEntity.ok(agendamentoService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Optional<Agendamento>> getById(@PathVariable Long id) {
        Optional<Agendamento> _agendamento = agendamentoService.findById(id);
        return _agendamento != null ? ResponseEntity.ok(_agendamento) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Agendamento> post(@RequestBody Agendamento agendamento) {
        return ResponseEntity.ok(agendamentoService.create(agendamento));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> put(@RequestBody Agendamento agendamento) {
        return agendamentoService.update(agendamento) ? ResponseEntity
                .ok(agendamento) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return agendamentoService.delete(id) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
