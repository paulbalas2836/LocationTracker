package com.proiect.SCD.service;

import com.proiect.SCD.dto.RegisterDto;
import com.proiect.SCD.exception.UserAlreadyExistAuthenticationException;
import com.proiect.SCD.utils.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class RegistrationService {
    private ConfirmationTokenService confirmationTokenService;

    private final AppUserService appUserService;
    public ResponseEntity register(RegisterDto registerDto){
        try {
            return new ResponseEntity(appUserService.signUpUser(registerDto), HttpStatus.OK);
       } catch (UserAlreadyExistAuthenticationException e) {
           return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()-> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "Confirmed";
    }
}
