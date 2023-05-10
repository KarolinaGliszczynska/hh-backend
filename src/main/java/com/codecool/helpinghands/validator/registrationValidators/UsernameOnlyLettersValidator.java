package com.codecool.helpinghands.validator.registrationValidators;

import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.validator.AbstractValidator;
import com.codecool.helpinghands.validator.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameOnlyLettersValidator extends AbstractValidator <RegistrationDTO>  {

    private static final String ONLY_LETTERS_PATTERN = "^[a-zA-Z]+$";
    @Override
    protected RegistrationDTO validateAndApplyNext(RegistrationDTO inputData) throws WrongInputException {
        String username = inputData.getUserNickname();
        if ( !username.matches( ONLY_LETTERS_PATTERN ) ) {
            throw new WrongInputException( "Username must contains letters only" );
        }
        inputData.setUserNickname(username.toUpperCase());
        return inputData;
    }
}
