package com.sac.backend.controllers;

import com.sac.backend.interfaces.Control;
import com.sac.backend.models.Datas;
import com.sac.backend.services.DatasService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/datas")

public class DatasController implements Control<Datas> {

    @Autowired
    private DatasService datasService;

    @Override
    @GetMapping(value = "/")
    public ResponseEntity<List<Datas>> getAll() {
        return ResponseEntity.ok(datasService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Datas> _datasModel = datasService.findById(id);
        return _datasModel != null ? ResponseEntity.ok(_datasModel) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Datas> post(@RequestBody Datas datasModel) {
        return ResponseEntity.ok(datasService.create(datasModel));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody Datas datasModel) {
        return datasService.update(datasModel) ? ResponseEntity.ok(datasModel) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return datasService.delete(id) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/status")
	public ResponseEntity<List<Datas>> getByStatus() {
		return ResponseEntity.ok(datasService.listarPorStatus());
	}

}
