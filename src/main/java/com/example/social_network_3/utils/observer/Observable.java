package com.example.social_network_3.utils.observer;


import com.example.social_network_3.utils.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
