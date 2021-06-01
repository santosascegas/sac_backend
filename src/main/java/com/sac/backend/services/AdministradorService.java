package com.sac.backend.services;

import com.sac.backend.exception.AuthorizedException;
import com.sac.backend.interfaces.AdministradorRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.AdministradorModel;
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
public class AdministradorService implements ServiceInterface<AdministradorModel> {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder pswdEnconder;

    @Autowired
    private JWTUtil jwtUtil;

    public AdministradorService() {}

    public AdministradorModel create(AdministradorModel admin) {
        admin.setSenha(pswdEnconder.encode(admin.getSenha()));
        return administradorRepository.save(admin);
    }

    @Override
    public AdministradorModel findById(Long id) throws AuthorizedException {
        if (!jwtUtil.authorized(id))
            throw new AuthorizedException("Acesso Negado!");

        Optional<AdministradorModel> _admin =
                administradorRepository.findById(id);
        return _admin.orElse(null);
    }

    @Override
    public List<AdministradorModel> findAll() {
        return (List<AdministradorModel>) administradorRepository.findAll();
    }

    public static UserDetailsImpl authenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (UserDetailsImpl) auth.getPrincipal();
        }
        return null;
    }

    public Optional<AdministradorModel> getAdministrador(Long id) {
        return administradorRepository.findById(id);
    }

    public void addAdministrador(AdministradorModel administradorModel) {
        administradorRepository.save(administradorModel);
    }

    public void updateAdministrador(String id, AdministradorModel administradorModel) {
        administradorRepository.save(administradorModel);
    }

    public void deleteAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }
}
