package com.proiect.SCD.controller;

import com.proiect.SCD.dto.ChangeAppUserPasswordDto;
import com.proiect.SCD.dto.RegisterDto;
import com.proiect.SCD.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:8100"})
public class UserController {
   private AppUserService appUserService;

    @GetMapping(path = "/getUser/{userEmail}")
    public ResponseEntity getUser(@PathVariable String userEmail){
        return new ResponseEntity(appUserService.loadUserByUsername(userEmail), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/changePasswordUser/{userEmail}")
    public ResponseEntity changePassword(@PathVariable String userEmail, @Valid @RequestBody ChangeAppUserPasswordDto changeAppUserPasswordDto){
        return new ResponseEntity(appUserService.updatePasswordAppUser(userEmail, changeAppUserPasswordDto), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/changeImageUser/{userEmail}")
    public ResponseEntity changeImage(@PathVariable String userEmail, @RequestPart("image") @Valid @NotNull @NotEmpty MultipartFile image) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        return new ResponseEntity(appUserService.updateImageAppUser(userEmail, image), HttpStatus.ACCEPTED);
    }

}
