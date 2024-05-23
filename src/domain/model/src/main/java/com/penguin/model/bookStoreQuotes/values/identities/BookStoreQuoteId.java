package com.penguin.model.bookStoreQuotes.values.identities;

import com.penguin.model.generic.Identity;

public class BookStoreQuoteId extends Identity {

    public BookStoreQuoteId(){
        super();
    }

    public BookStoreQuoteId(String id) {
        super(id);
    }

    public static BookStoreQuoteId of(String uuid){
        return new BookStoreQuoteId(uuid);
    }
}
