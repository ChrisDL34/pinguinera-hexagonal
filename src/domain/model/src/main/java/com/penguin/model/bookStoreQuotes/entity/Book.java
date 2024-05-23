package com.penguin.model.bookStoreQuotes.entity;

import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.CopyId;

public class Book extends Copy{

    private Book(Title title, Author author, Stock stock, Price price, PublicationYear publicationYear) {
        super(title, author, stock, price, publicationYear);
        calculateIndividualPrice();
    }

    public Book(CopyId id) {
        super(id);
    }

    public static Book from(
            Title title,
            Author author,
            Stock stock,
            PublicationYear publicationYear,
            Price price
    ){
        return new Book(title, author, stock, price, publicationYear);
    }

    @Override
    public void calculateIndividualPrice() {
        double currentPrice = this.getPrice().value();
        double newPrice = currentPrice * (1 + 1.0 / 3.0);
        this.setPrice(new Price(newPrice));
    }
}
