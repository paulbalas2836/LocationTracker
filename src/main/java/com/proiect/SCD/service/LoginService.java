package com.proiect.SCD.service;

import com.proiect.SCD.domain.AppUser;
import com.proiect.SCD.dto.AuthenticationDto;
import com.proiect.SCD.exception.AppException;
import com.proiect.SCD.security.PasswordDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private PasswordDecoder decoder;

    public String loginAccount(AuthenticationDto authenticationDto) {
        boolean emailBoolean = this.appUserService.emailExist(authenticationDto.getEmail());
        boolean passwordBool = false;
        if (emailBoolean) {
            passwordBool =
                    decoder.passwordDecoder(
                            authenticationDto.getPassword(),
                            this.appUserService.loadUserByUsername(authenticationDto.getEmail()).getPassword());
        }

        if (!emailBoolean || !passwordBool) {
            throw new AppException(HttpStatus.NOT_FOUND, "Password or email incorrect!");
        }

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(authenticationDto.getEmail());
        if(!appUser.getEnabled())
        {
            throw new AppException(HttpStatus.BAD_REQUEST, "Account is not enable!");
        }

        return this.appUserService
                .getJwtUtil()
                .createToken(
                        this.getClaims((AppUser) this.appUserService.loadUserByUsername(authenticationDto.getEmail())));
    }
    public Map<String, Object> getClaims(AppUser appUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", appUser.getEmail());
        map.put("id", appUser.getId());
        return map;
    }


}
