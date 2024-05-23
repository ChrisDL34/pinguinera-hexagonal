package com.penguin.model.bookStoreQuotes.values.identities;

import com.penguin.model.generic.Identity;

public class CustomerId extends Identity {

    public CustomerId(){
        super();
    }

    public CustomerId(String id) {
        super(id);
    }

    public static CustomerId of(String uuid){
        return new CustomerId(uuid);
    }
}
