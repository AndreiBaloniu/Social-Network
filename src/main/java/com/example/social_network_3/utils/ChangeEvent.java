package com.example.social_network_3.utils;

import java.util.List;

public class ChangeEvent<E> implements Event {
    private ChangeEventType type;
    private E entity;

    public ChangeEvent(ChangeEventType type, E entity) {
        this.type = type;
        this.entity = entity;
    }

    public ChangeEventType getType() {
        return type;
    }

    public E getEntity() {
        return entity;
    }
}




