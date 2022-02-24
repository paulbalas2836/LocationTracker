package com.proiect.SCD.dto;

import com.proiect.SCD.annotations.RegisterPasswordMatches;
import com.proiect.SCD.annotations.UpdatePasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@UpdatePasswordMatches(message = "The confirm password doesn't match the password")
@NoArgsConstructor
public class ChangeAppUserPasswordDto {
    @NotNull(message = "Old password is required")
    @NotEmpty(message = "Old password should not be empty")
    private String oldPassword;

    @NotNull(message = "The password is required.")
    @NotEmpty(message = "The password must not be empty")
    private String newPassword;
    private String newMatchingPassword;
}
