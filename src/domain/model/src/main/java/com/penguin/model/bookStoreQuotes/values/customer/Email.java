package com.penguin.model.bookStoreQuotes.values.customer;

import com.penguin.model.generic.ValueObject;

import java.util.regex.Pattern;

public class Email implements ValueObject<String> {

    private  String email;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public Email() {
    }

    @Override
    public String value() {
        return email;
    }
}
