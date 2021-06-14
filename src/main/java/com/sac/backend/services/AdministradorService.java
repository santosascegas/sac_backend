package com.sac.backend.services;

import com.sac.backend.exception.AuthorizedException;
import com.sac.backend.interfaces.AdministradorRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.Administrador;
import com.sac.backend.security.JWTUtil;
import com.sac.backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService implements ServiceInterface<Administrador> {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder pswdEnconder;

    @Autowired
    private JWTUtil jwtUtil;

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
    public Administrador findById(Long id) throws AuthorizedException {
        if (!jwtUtil.authorized(id))
            throw new AuthorizedException("Acesso Negado!");

        Optional<Administrador> _admin =
                administradorRepository.findById(id);
        return _admin.orElse(null);
    }

    @Override
    public List<Administrador> findAll() {
        return (List<Administrador>) administradorRepository.findAll();
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
