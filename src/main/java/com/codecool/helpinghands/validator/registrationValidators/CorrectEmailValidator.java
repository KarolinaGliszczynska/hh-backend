package com.codecool.helpinghands.validator.registrationValidators;

import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.validator.AbstractValidator;
import com.codecool.helpinghands.validator.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CorrectEmailValidator extends AbstractValidator<RegistrationDTO> {
    @Override
    protected RegistrationDTO validateAndApplyNext(RegistrationDTO inputData) throws WrongInputException {
        String email = inputData.getUserEmail();
        if (!email.matches("^(.+)@(\\S+)$")) {
            throw new WrongInputException("Please enter correct email");
        }
        return inputData;
    }
}
