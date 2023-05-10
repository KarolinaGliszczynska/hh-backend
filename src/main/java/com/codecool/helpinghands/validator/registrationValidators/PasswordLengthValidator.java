package com.codecool.helpinghands.validator.registrationValidators;

import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.validator.AbstractValidator;
import com.codecool.helpinghands.validator.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordLengthValidator extends AbstractValidator <RegistrationDTO>  {
    @Override
    protected RegistrationDTO validateAndApplyNext(RegistrationDTO inputData) throws WrongInputException {
        String password = inputData.getPassword();
        if (password.length()<8) {
            throw new WrongInputException("Password must be at least 8 characters");
        }
        return inputData;
    }
}
