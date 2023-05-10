package com.codecool.helpinghands.validator;

public class LastValidator <T> implements ValidatorChain <T>{
    @Override
    public T validate(T input) throws WrongInputException {
        return input;
    }

    @Override
    public void setNext(ValidatorChain<T> nextValidator) {

    }
}
