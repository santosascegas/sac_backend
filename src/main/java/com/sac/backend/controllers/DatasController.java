package com.sac.backend.controllers;

import com.sac.backend.interfaces.InterfaceControle;
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

import java.util.Date;
import java.util.List;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 18:03
 * Created on IntelliJ IDEA
 */

@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "/datas")
public class DatasController implements InterfaceControle<DatasModel> {

    @Autowired
    private DatasService datasService;

    @Override
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(datasService.findAll());
    }

    @Override
    @GetMapping(value = "{data}")
    public ResponseEntity<?> getByData(@PathVariable("datas")Date date) {
        DatasModel _datasModel = datasService.findByData(date);
        if (_datasModel != null)
            return ResponseEntity.ok(_datasModel);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping
    public ResponseEntity post(@RequestBody DatasModel datasModel) {
        return ResponseEntity.ok(datasService.create(datasModel));
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(@RequestBody DatasModel datasModel) {
        if (datasService.update(datasModel))
            return ResponseEntity.ok(datasModel);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
