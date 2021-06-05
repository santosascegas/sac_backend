package com.sac.backend.controllers;

import com.sac.backend.exception.AuthorizedException;
import com.sac.backend.interfaces.Control;
import com.sac.backend.models.AdministradorModel;
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
@RequestMapping("/login")
public class AdministradorController implements Control<AdministradorModel> {

    @Autowired
    private AdministradorService administradorService;

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (administradorService.delete(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<AdministradorModel>> getAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            AdministradorModel _admin = administradorService.findById(id);
            if (_admin != null)
                return ResponseEntity.ok(_admin);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<AdministradorModel> post(@RequestBody
                AdministradorModel administradorModel) {
        administradorService.create(administradorModel);
        return ResponseEntity.ok(administradorModel);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody AdministradorModel administradorModel) {
        if (administradorService.update(administradorModel))
            return ResponseEntity.ok(administradorModel);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
