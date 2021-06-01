package com.sac.backend.controllers;

import com.sac.backend.models.AdministradorModel;
import com.sac.backend.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces =
            "application/json")
    public void addUsuario(@RequestBody AdministradorModel administradorModel) {
        administradorService.addAdministrador(administradorModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces =
            "application/json")
    public void updateAdministrador(@RequestBody AdministradorModel administradorModel,
            @PathVariable String id) {
        administradorService.updateAdministrador(id, administradorModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces
            = "application/jason")
    public void deleteAdministrador(@PathVariable Long id) {
        administradorService.deleteAdministrador(id);
    }
}
