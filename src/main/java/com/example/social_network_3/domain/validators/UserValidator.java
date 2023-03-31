package com.example.social_network_3.domain.validators;


import com.example.social_network_3.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {
        if (user.getFirst_name() == null || user.getFirst_name().isEmpty()) {
            throw new ValidationException("Numele utilizatorului nu poate fi gol!");
        }
        if (user.getLast_name() == null || user.getLast_name().isEmpty()) {
            throw new ValidationException("Prenumele utilizatorului nu poate fi gol!");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("Username-ul utilizatorului nu poate fi gol!");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Parola utilizatorului nu poate fi goala!");
        }
    }
}
