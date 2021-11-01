package com.sac.backend.controllers;

import java.util.List;
import java.util.Optional;

import com.sac.backend.interfaces.Control;
import com.sac.backend.models.Agendamento;
import com.sac.backend.services.AgendamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController implements Control<Agendamento> {

    @Autowired
    private AgendamentoService agendamentoService;

    @Override
    @GetMapping(value = "/")
    public ResponseEntity<List<Agendamento>> getAll() {
        return ResponseEntity.ok(agendamentoService.findAll());
    }
  
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Agendamento>> getById(@PathVariable Long id) {
        Optional<Agendamento> _agendamento = agendamentoService.findById(id);
        return _agendamento != null ? ResponseEntity.ok(_agendamento) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping(value = "/")
    public ResponseEntity<Agendamento> post(@RequestBody Agendamento agendamento) {
        return ResponseEntity.ok(agendamentoService.create(agendamento));
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody Agendamento agendamento) {
        return agendamentoService.update(agendamento) ? ResponseEntity
                .ok(agendamento) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return agendamentoService.delete(id) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
