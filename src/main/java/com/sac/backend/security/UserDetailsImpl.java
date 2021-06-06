package com.sac.backend.security;

import com.sac.backend.models.TipoPerfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final Long serialVersionUID = 1L;

    private Long id;
    private String login;
    private String senha;
    private Collection<? extends GrantedAuthority> auth;

    public UserDetailsImpl() {}

    public UserDetailsImpl(Long id, String login, String senha,
                           Set<TipoPerfil> perfis) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.auth = perfis.stream().map(p -> new SimpleGrantedAuthority(
                p.getDesc())).collect(Collectors.toList());
    }

    public boolean hasRole(TipoPerfil tipoPerfil) {
        return getAuthorities().contains(new SimpleGrantedAuthority(
                tipoPerfil.getDesc()));
    }

    public Long getId() { return id; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return auth; }
    @Override
    public String getPassword() { return senha; }
    @Override
    public String getUsername() { return login; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
