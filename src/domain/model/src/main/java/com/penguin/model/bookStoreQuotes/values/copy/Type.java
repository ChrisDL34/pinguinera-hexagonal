package com.penguin.model.bookStoreQuotes.values.copy;

import com.penguin.model.generic.ValueObject;

public class Type implements ValueObject<String> {

    private  String type;

//    public Type(String type) {
//        if (type == null) {
//            throw new IllegalArgumentException("Type cannot be null");
//        }
//        this.type = type;
//    }

    public Type(String type) {
        this.type = type;
    }

    public Type() {
    }

    @Override
    public String value() {
        return this.type;
    }
}

