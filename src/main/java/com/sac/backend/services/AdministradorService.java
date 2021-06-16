package com.sac.backend.services;

import com.sac.backend.exception.AuthorizedException;
import com.sac.backend.interfaces.AdministradorRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService implements ServiceInterface<Administrador> {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder pswdEnconder;

    public AdministradorService() {}

    @Override
    public Administrador create(Administrador admin) {
        admin.setSenha(pswdEnconder.encode(admin.getSenha()));
        return administradorRepository.save(admin);
    }

    @Override
    public boolean delete(Long id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Administrador> findById(Long id) throws AuthorizedException {
        Optional<Administrador> _admin =
                administradorRepository.findById(id);
        return _admin;
    }

    @Override
    public List<Administrador> findAll() {
        List<Administrador> admins = new ArrayList<>();
        administradorRepository.findAll().forEach(admins::add);

        return admins;
    }

    @Override
    public boolean update(Administrador obj) {
        if (administradorRepository.existsById(obj.getId())) {
            obj.setSenha(pswdEnconder.encode(obj.getSenha()));
            administradorRepository.save(obj);
            return true;
        }
        return false;
    }
}
