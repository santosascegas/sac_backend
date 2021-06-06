package com.sac.backend.security;

import com.sac.backend.interfaces.AdministradorRepository;
import com.sac.backend.models.AdministradorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdministradorRepository repo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdministradorModel admin = repo.findByLogin(s);
        if (admin == null)
            throw new UsernameNotFoundException(s);

        return new UserDetailsImpl(admin.getId(), admin.getLogin(),
                admin.getSenha(), admin.getPerfil());
    }
}
