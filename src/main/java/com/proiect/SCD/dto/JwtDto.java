package com.proiect.SCD.dto;

import com.proiect.SCD.domain.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    String jwt;
    AppUserDto appUserDto;
}
