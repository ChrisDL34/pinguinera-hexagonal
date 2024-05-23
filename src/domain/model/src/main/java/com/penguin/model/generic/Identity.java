package com.penguin.model.generic;

import java.util.Objects;
import java.util.UUID;

public class Identity implements ValueObject<String> {

    private final String uuid;

    protected Identity(){
        this.uuid = UUID.randomUUID().toString();
    }

    public Identity(String id) {
        this.uuid = id;
    }

    @Override
    public String value() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity identity = (Identity) o;
        return Objects.equals(uuid, identity.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
