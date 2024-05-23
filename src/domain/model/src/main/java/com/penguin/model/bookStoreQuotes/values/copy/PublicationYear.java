package com.penguin.model.bookStoreQuotes.values.copy;

import com.penguin.model.generic.ValueObject;


public class PublicationYear implements ValueObject<Integer> {

    private  Integer publicationYear;

    public PublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public PublicationYear() {
    }

    @Override
    public Integer value() {
        return this.publicationYear;
    }
}
