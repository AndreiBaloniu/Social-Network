package com.example.social_network_3.domain.validators;

import com.example.social_network_3.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship>{
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        if (friendship.getUser1() == 0) {
            throw new ValidationException("Id-ul nu poate fi 0!");
        }
        if (friendship.getUser2() == 0) {
            throw new ValidationException("Id-ul nu poate fi 0!");
        }
        if (friendship.getDate() == null) {
            throw new ValidationException("Data crearii nu poate fi goala!");
        }
    }

}