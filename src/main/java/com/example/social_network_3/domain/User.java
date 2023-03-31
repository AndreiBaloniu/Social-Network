package com.example.social_network_3.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User extends Entity<Long>{

    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private Map<Long, User> friends;

    public User(String first_name, String last_name, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        friends = new HashMap<>();
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getFirst_name(), user.getFirst_name()) && Objects.equals(getLast_name(), user.getLast_name()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name(), getUsername(), getPassword());
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
