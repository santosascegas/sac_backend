package com.sac.backend;

import com.sac.backend.appuser.AppUser;
import com.sac.backend.appuser.AppUserRepository;
import com.sac.backend.appuser.AppUserRole;
import com.sac.backend.files.FileService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUsersAtStartup implements CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        fileService.init();
        appUserRepository.save(new AppUser("admin", "admin@admin.com", bCryptPasswordEncoder.encode("admin"), AppUserRole.ADMIN, true));
        appUserRepository.save(new AppUser("user", "user@user.com", bCryptPasswordEncoder.encode("user"), AppUserRole.USER, true));
    }
}