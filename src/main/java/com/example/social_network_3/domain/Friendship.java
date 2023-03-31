package com.example.social_network_3.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Friendship extends Entity<Long>{

    private Long user1;
    private Long user2;
    private Date date;

    public Friendship() {
    }

    public Friendship(Long user1, Long user2, Date date) {
        this.user1 = user1;
        this.user2 = user2;
        this.date = date;
    }

    public Friendship(Long id, Long user1, Long user2, Date date) {
        this.setId(id);
        this.user1 = user1;
        this.user2 = user2;
        this.date = date;
    }

    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public Long getUser2() {
        return user2;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

}
