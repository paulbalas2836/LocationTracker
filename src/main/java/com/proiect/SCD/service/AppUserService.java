package com.proiect.SCD.service;

import com.proiect.SCD.domain.AppUser;
import com.proiect.SCD.domain.AppUserRole;
import com.proiect.SCD.dto.AppUserDto;
import com.proiect.SCD.dto.ChangeAppUserPasswordDto;
import com.proiect.SCD.dto.RegisterDto;
import com.proiect.SCD.exception.AppException;
import com.proiect.SCD.exception.UserAlreadyExistAuthenticationException;
import com.proiect.SCD.repository.AppUserRepository;
import com.proiect.SCD.security.PasswordDecoder;
import com.proiect.SCD.utils.ConfirmationToken;
import com.proiect.SCD.utils.EmailSender;
import com.proiect.SCD.utils.FileUploadUtil;
import com.proiect.SCD.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    @Autowired
    private PasswordDecoder decoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public AppUser findAppUserById(Integer id){
        return appUserRepository.findById(id).orElseThrow(()-> new AppException(HttpStatus.BAD_REQUEST, "User "+ id + " was not found"));
    }

    public String signUpUser(RegisterDto registerDto) throws UserAlreadyExistAuthenticationException {
        if (emailExist(registerDto.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                    + registerDto.getEmail());
        }
        AppUser appUser = new AppUser();
        appUser.setAppUserRole(AppUserRole.USER);
        appUser.setEnabled(false);
        appUser.setEmail(registerDto.getEmail());
        appUser.setImage("./images/user_photos_default.png");
      String encodedPassword = bCryptPasswordEncoder.encode(registerDto.getPassword());
      appUser.setPassword(encodedPassword);
      String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(appUser, token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));
     confirmationTokenService.saveConfirmationToken(confirmationToken);
     String link = "http://localhost:8080/register/confirm?token=" + token;
     emailSender.send(link, registerDto.getEmail());
      return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public JwtUtil getJwtUtil() {
        return this.jwtUtil;
    }

    public boolean emailExist(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    public AppUserDto getAppUserById(Integer userId){
        AppUser appUser;
        if(appUserRepository.findById(userId).isPresent())
        {
             appUser = appUserRepository.findById(userId).get();
        }
        else throw new AppException(HttpStatus.BAD_REQUEST, "User "+ userId + " was not found");

        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setImage(appUser.getImage());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setAppUserRole(appUser.getAppUserRole());
        return appUserDto;
    }

    public AppUserDto getAppUserByClaims(Claims claims){
        Integer userId = jwtUtil.extractId(claims);
        return getAppUserById(userId);
    }

    public AppUser updatePasswordAppUser(String appUserEmail, ChangeAppUserPasswordDto changeAppUserPasswordDto){
        AppUser appUser = (AppUser) loadUserByUsername(appUserEmail);

        if(!decoder.passwordDecoder(changeAppUserPasswordDto.getOldPassword(), appUser.getPassword())){
            throw new AppException(HttpStatus.BAD_REQUEST, "Current password doesn't match");
        }
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(loadUserByUsername(appUserEmail))){
            appUser.setPassword(bCryptPasswordEncoder.encode(changeAppUserPasswordDto.getNewPassword()));
        }
        return appUserRepository.save(appUser);
    }

    public AppUser updateImageAppUser(String appUserEmail, MultipartFile image) throws IOException {
        AppUser appUser = (AppUser) loadUserByUsername(appUserEmail);
        String filename = "my_image_"+appUser.getId();
        String uploadDir= "./src/frontend/public/images/";
        FileUploadUtil.saveFile(uploadDir, filename+".png", image);
        appUser.setImage("./images/"+filename+".png");
        return appUserRepository.save(appUser);
    }
}
