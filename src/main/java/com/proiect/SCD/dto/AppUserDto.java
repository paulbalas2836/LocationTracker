package com.proiect.SCD.dto;

import com.proiect.SCD.domain.AppUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDto {
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String image;

    @NotEmpty
    @NotNull
    private AppUserRole appUserRole;
}
