package com.proiect.SCD.controller;

import com.proiect.SCD.dto.AuthenticationDto;
import com.proiect.SCD.dto.JwtDto;
import com.proiect.SCD.service.AppUserService;
import com.proiect.SCD.service.LoginService;
import com.proiect.SCD.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:8100"})
public class LoginController {

    private LoginService loginService;
    private JwtUtil jwtTokenUtil;
    private AppUserService appUserService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto authenticationDto) throws Exception {
        try {
            String jwt = this.loginService.loginAccount(authenticationDto);
            return new ResponseEntity(
                    new JwtDto(jwt, appUserService.getAppUserByClaims(this.jwtTokenUtil.extractAllClaims(jwt))),
                    HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }
}
