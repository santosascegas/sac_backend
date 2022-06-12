package com.sac.backend.appuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserService implements UserDetailsService {
    private static final String USER_OR_EMAIL_ALREADY_EXISTS = "O NOME DE USUÁRIO OU O EMAIL-> '%s' ou '%s' JÁ EXISTE!";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (AppUserRole role : AppUserRole.values()) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }

        return new User(appUser.get().getUsername(), appUser.get().getPassword(), authorities);
    }

    public Boolean saveAppUser(AppUser appUser) {
        boolean usernameExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        boolean emailExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (usernameExists || emailExists) {
            throw new IllegalStateException(String.format(USER_OR_EMAIL_ALREADY_EXISTS, appUser.getUsername(), appUser.getEmail()));
        }
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);

        return true;
    }

    public Optional<AppUser> getAppUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
