package com.penguin.model.bookStoreQuotes.entity;

import com.penguin.model.bookStoreQuotes.values.customer.DateRegister;
import com.penguin.model.bookStoreQuotes.values.customer.Email;
import com.penguin.model.bookStoreQuotes.values.customer.Name;
import com.penguin.model.bookStoreQuotes.values.customer.Password;
import com.penguin.model.bookStoreQuotes.values.identities.CustomerId;
import com.penguin.model.generic.Entity;

public class Customer extends Entity<CustomerId> {

    private final Name name;
    private final Email email;
    private final Password password;
    private final DateRegister date;

    public Customer(Name name, Email email, Password password, DateRegister date) {
        super(new CustomerId());
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public static Customer from(
            Name name,
            Email email,
            Password password,
            DateRegister date
    ){
        return new Customer(name, email, password, date);
    }

    public void calculateDiscount(){}

    public void calculateSeniority(){}
}
