package com.sac.backend.agenda;

import com.sac.backend.interfaces.Control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/agenda")
public class AgendaController implements Control<Agenda>{

    @Autowired
    private AgendaService agendaService;

    @Override
    @GetMapping(value = "/")
    public ResponseEntity<List<Agenda>> getAll() {
        return ResponseEntity.ok(agendaService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Agenda> result = agendaService.findById(id);
        return result.isPresent() ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Agenda> post(@RequestBody Agenda obj) {
        return ResponseEntity.ok(agendaService.create(obj));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody Agenda obj) {
        return agendaService.update(obj) ? ResponseEntity.ok(obj) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(agendaService.delete(id));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<Agenda>> getByStatus() {
        return ResponseEntity.ok(agendaService.listOnlyAvailable());
    }
}
