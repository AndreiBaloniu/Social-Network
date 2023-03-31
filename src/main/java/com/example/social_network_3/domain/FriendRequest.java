package com.example.social_network_3.domain;

import java.sql.Date;

public class FriendRequest extends Entity<Long> {
    private Long senderId;
    private Long receiverId;
    private Date date;

    public FriendRequest() {

    }

    public FriendRequest(Long senderId, Long receiverId, Date date) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}