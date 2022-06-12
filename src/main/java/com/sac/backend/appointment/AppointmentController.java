package com.sac.backend.appointment;

import com.sac.backend.interfaces.Control;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
@AllArgsConstructor
public class AppointmentController implements Control<Appointment> {

    private final AppointmentService appointmentService;

    @Override
    @GetMapping(value = "/")
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Appointment>> getById(@PathVariable Long id) {
        Optional<Appointment> result = appointmentService.findById(id);
        return result.isPresent() ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Appointment> post(@RequestBody Appointment obj) {
        return ResponseEntity.ok(appointmentService.create(obj));
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody Appointment obj) {
        return appointmentService.update(obj) ? ResponseEntity.ok(obj) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return appointmentService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
