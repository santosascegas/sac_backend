package com.sac.backend.security;

import com.sac.backend.appuser.AppUser;
import com.sac.backend.appuser.AppUserRepository;
import com.sac.backend.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateAdminAccountOnStartup implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        AppUser admin = new AppUser("admin", "admin@admin.com", bCryptPasswordEncoder.encode("admin"), AppUserRole.ADMIN, true);
        appUserRepository.save(admin);
    }
}
