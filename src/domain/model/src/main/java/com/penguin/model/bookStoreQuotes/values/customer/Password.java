package com.penguin.model.bookStoreQuotes.values.customer;

import com.penguin.model.generic.ValueObject;

public class Password implements ValueObject<String> {

    private  String password;

    public Password(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    public Password() {
    }

    @Override
    public String value() {
        return password;
    }
}
