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
public class UniqueUsernameValidator extends AbstractValidator<RegistrationDTO> {

    @Autowired
    UserRepository userService;
    @Override
    protected RegistrationDTO validateAndApplyNext(RegistrationDTO inputData) throws WrongInputException {
        String username = inputData.getUserNickname();
        if (userService.findByUserNickname(username).isPresent()) {
            throw new WrongInputException( "Username already exists!" );
        }
        return inputData;
    }
}
