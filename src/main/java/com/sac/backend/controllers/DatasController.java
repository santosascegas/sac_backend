package com.sac.backend.controllers;

import com.sac.backend.interfaces.Control;
import com.sac.backend.models.DatasModel;
import com.sac.backend.services.DatasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 18:03
 * Created on IntelliJ IDEA
 */

@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "/datas")
public class DatasController implements Control<DatasModel> {

    @Autowired
    private DatasService datasService;

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return datasService.delete(id) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<DatasModel>> getAll() {
        return ResponseEntity.ok(datasService.findAll());
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        DatasModel _datasModel = datasService.findById(id);
        return _datasModel != null ? ResponseEntity.ok(_datasModel) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping
    public ResponseEntity post(@RequestBody DatasModel datasModel) {
        return ResponseEntity.ok(datasService.create(datasModel));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody DatasModel datasModel) {
        return datasService.update(datasModel) ? ResponseEntity.ok(datasModel) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
