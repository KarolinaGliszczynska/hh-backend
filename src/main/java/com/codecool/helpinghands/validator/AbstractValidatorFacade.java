package com.codecool.helpinghands.validator;

import java.util.List;

public abstract class AbstractValidatorFacade<T> {

    private final ValidatorChain<T> headValidator;

    public AbstractValidatorFacade( List<ValidatorChain<T>> validators ) {
        this.headValidator = buildChain( validators );
    }

    public T validate( T input ) throws WrongInputException {
        return headValidator.validate( input );
    }

    private ValidatorChain<T> buildChain( List<ValidatorChain<T>> validators ) {
        if ( validators.isEmpty() ) {
            return new LastValidator<>();
        }
        for ( int i = 0; i < validators.size(); i++ ) {
            ValidatorChain<T> currentValidator = validators.get( i );
            ValidatorChain<T> nextValidator = i < validators.size() - 1 ? validators.get( i + 1 ) : new LastValidator<T>();
            currentValidator.setNext( nextValidator );
        }
        return validators.get( 0 );
    }
}
