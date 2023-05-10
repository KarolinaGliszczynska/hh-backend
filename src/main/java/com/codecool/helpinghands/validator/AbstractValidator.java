package com.codecool.helpinghands.validator;

public abstract class AbstractValidator <T> implements ValidatorChain<T> {

    private ValidatorChain<T> nextValidator;

    @Override
    public T validate(T input) throws WrongInputException {
        var validatedInput = validateAndApplyNext( input );
        return nextValidator.validate( validatedInput );
    }

    protected abstract T validateAndApplyNext( T input ) throws WrongInputException;

    @Override
    public void setNext(ValidatorChain<T> nextValidator) {
        this.nextValidator = nextValidator;
    }
}
