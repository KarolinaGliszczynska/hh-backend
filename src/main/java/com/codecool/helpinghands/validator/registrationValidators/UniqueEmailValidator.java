package com.codecool.helpinghands.validator.registrationValidators;

import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.repository.UserRepository;
import com.codecool.helpinghands.validator.AbstractValidator;
import com.codecool.helpinghands.validator.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator extends AbstractValidator<RegistrationDTO, WrongInputException> {

    @Autowired
    UserRepository userService;

    @Override
    protected RegistrationDTO validateAndApplyNext(RegistrationDTO inputData) throws WrongInputException {
        String email = inputData.getUserEmail();
        if ( userService.findByUserEmail(email) != null ) {
            throw new WrongInputException( "Email already exists!" );
        }
        return inputData;
    }
}