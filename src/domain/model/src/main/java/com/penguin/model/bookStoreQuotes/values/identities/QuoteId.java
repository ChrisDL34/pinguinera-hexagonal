package com.penguin.model.bookStoreQuotes.values.identities;

import com.penguin.model.generic.Identity;

public class QuoteId extends Identity {

    public QuoteId(){
        super();
    }

    public QuoteId(String id) {
        super(id);
    }

    public static QuoteId of(String uuid){
        return new QuoteId(uuid);
    }
}
