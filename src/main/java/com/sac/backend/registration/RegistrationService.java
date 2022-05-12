package com.sac.backend.registration;

import com.sac.backend.appuser.AppUser;
import com.sac.backend.appuser.AppUserRole;
import com.sac.backend.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException(
                    String.format("O EMAIL '{}' NÃO É VALIDO!", request.getEmail())
            );
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER,
                        false
                )
        );
    }
}
