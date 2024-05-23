package com.penguin.model.generic;

import java.util.Objects;

public abstract class Entity<I extends Identity> {

    private final I id;

    public Entity(I id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        return id.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public I identity(){
        return id;
    }
}
