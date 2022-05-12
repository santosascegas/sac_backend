package com.sac.backend.appuser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "NENHUM USUÁRIO COM O EMAIL -> '%s' FOI ENCONTRADO!";
    private static final String USER_ALREADY_EXISTS = "O NOME DE USUÁRIO -> '%s' NÃO ESTÁ DISPONÍVEL!";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow( () ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, username))
                );
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();

        if (userExists) {
            throw new IllegalStateException(String.format(USER_ALREADY_EXISTS, appUser.getUsername()));
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        return "USUÁRIO CRIADO COM SUCESSO!";
    }
}
