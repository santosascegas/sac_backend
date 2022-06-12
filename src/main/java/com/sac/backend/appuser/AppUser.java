package com.sac.backend.appuser;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean isEnabled = false;

    public AppUser(String username, String email, String password, AppUserRole appUserRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    public AppUser(String username, String email, String password, AppUserRole appUserRole, Boolean isEnabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.isEnabled = isEnabled;
    }
}