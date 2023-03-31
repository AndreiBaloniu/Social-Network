package com.example.social_network_3.domain.validators;

import com.example.social_network_3.domain.FriendRequest;

public class FriendRequestValidator implements Validator<FriendRequest>{

    public void validate(FriendRequest friendRequest) {
        if (friendRequest.getSenderId()== 0) {
            throw new ValidationException("Id-ul nu poate fi 0!");
        }
        if (friendRequest.getReceiverId()== 0) {
            throw new ValidationException("Id-ul nu poate fi 0!");
        }
        if (friendRequest.getDate() == null) {
            throw new ValidationException("Invalid date");
        }
    }


}
