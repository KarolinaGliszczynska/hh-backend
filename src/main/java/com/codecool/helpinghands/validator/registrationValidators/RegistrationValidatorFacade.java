package com.codecool.helpinghands.validator.registrationValidators;

import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.validator.AbstractValidatorFacade;
import com.codecool.helpinghands.validator.ValidatorChain;
import com.codecool.helpinghands.validator.WrongInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationValidatorFacade extends AbstractValidatorFacade <RegistrationDTO> {

    public RegistrationValidatorFacade(List<ValidatorChain<RegistrationDTO>> validators) {
        super(validators);
    }
}
