package com.sac.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/diasagendados")
public class DiasAgendadosController {

    @Autowired
    private List<Date> diasDisponiveis;

    //@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public DiasAgendadosController() {
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    public void validacaoDia() {
        if (diasAgendamento.disponivel) {
            diasDisponiveis.add(diasAgendamento);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public List<Date> listarDias(){
        return diasDisponiveis;
    }
}