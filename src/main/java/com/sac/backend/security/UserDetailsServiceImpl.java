package com.sac.backend.security;

import com.sac.backend.interfaces.UsuarioRepository;
import com.sac.backend.models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario admin = repo.findByLogin(s);
        if (admin == null)
            throw new UsernameNotFoundException(s);

        return new UserDetailsImpl(admin.getId(), admin.getLogin(),
                admin.getSenha(), admin.getPerfis());
    }
}
