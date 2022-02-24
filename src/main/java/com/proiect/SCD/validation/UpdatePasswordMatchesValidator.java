package com.proiect.SCD.validation;

import com.proiect.SCD.annotations.RegisterPasswordMatches;
import com.proiect.SCD.annotations.UpdatePasswordMatches;
import com.proiect.SCD.dto.ChangeAppUserPasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdatePasswordMatchesValidator implements ConstraintValidator<UpdatePasswordMatches, Object> {
    @Override
    public void initialize(UpdatePasswordMatches constraintAnnotation){}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        ChangeAppUserPasswordDto changeAppUserPasswordDto = (ChangeAppUserPasswordDto) obj;
        return changeAppUserPasswordDto.getNewPassword().equals(changeAppUserPasswordDto.getNewMatchingPassword());
    }

}
