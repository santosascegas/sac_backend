package com.sac.backend.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {

    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
}
