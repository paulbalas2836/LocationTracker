package com.proiect.SCD.validation;

import com.proiect.SCD.annotations.RegisterPasswordMatches;
import com.proiect.SCD.dto.RegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<RegisterPasswordMatches, Object> {
    @Override
    public void initialize(RegisterPasswordMatches constraintAnnotation){}
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegisterDto registerDto = (RegisterDto) obj;
        return registerDto.getPassword().equals(registerDto.getMatchingPassword());
    }
}
