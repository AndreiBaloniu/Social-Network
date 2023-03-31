package com.example.social_network_3.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}