package com.example.social_network_3.utils.observer;
import com.example.social_network_3.utils.Event;

public interface Observer<E extends Event> {
    void update(E e);
}