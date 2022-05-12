package com.sac.backend.registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
