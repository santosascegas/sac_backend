package com.sac.backend.controllers;


import com.sac.backend.models.FaleConoscoModel;
import com.sac.backend.services.FaleConoscoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fale-conosco")
public class FaleConoscoController {

    @Autowired
    private FaleConoscoService faleConoscoService;
    
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> sendFaleConosco(@RequestBody FaleConoscoModel faleConosco) {
        return faleConoscoService.sendFaleConosco(faleConosco) 
            ? ResponseEntity.status(200).build() : ResponseEntity.status(500).build();
    }

}