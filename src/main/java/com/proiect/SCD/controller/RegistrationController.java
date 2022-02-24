package com.proiect.SCD.controller;

import com.proiect.SCD.dto.RegisterDto;
import com.proiect.SCD.service.RegistrationService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:8100"})
public class RegistrationController {
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto) {
            return new ResponseEntity(registrationService.register(registerDto),HttpStatus.CREATED);
    }

    @GetMapping(path ="confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
