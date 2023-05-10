package com.codecool.helpinghands.validator;

public interface ValidatorChain<T> {

    T validate(T input) throws WrongInputException;

    void setNext(ValidatorChain<T> nextValidator);
}