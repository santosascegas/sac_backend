package com.sac.backend.controllers;

import com.sac.backend.exception.AuthorizedException;
import com.sac.backend.interfaces.Control;
import com.sac.backend.models.Administrador;
import com.sac.backend.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministradorController implements Control<Administrador> {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping(value="/")
    @Override
    public ResponseEntity<List<Administrador>> getAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            Administrador _admin = administradorService.findById(id);
            return _admin != null ? ResponseEntity.ok(_admin) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<Administrador> post(@RequestBody
                Administrador administrador) {
        return ResponseEntity.ok(administradorService.create(administrador));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody Administrador administrador) {
        return administradorService.update(administrador) ? ResponseEntity
                .ok(administrador) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return administradorService.delete(id) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
