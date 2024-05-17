package com.penguin.model.bookStoreQuotes;

import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.events.BookStoreQuotesCreated;
import com.penguin.model.bookStoreQuotes.factory.CopyFactory;
import com.penguin.model.bookStoreQuotes.factory.CopyFactoryImpl;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.generic.EventChange;

import java.util.ArrayList;

public class BookStoreQuotesEventChange extends EventChange {

    private final CopyFactory copyFactory = new CopyFactoryImpl();

    public BookStoreQuotesEventChange(BookStoreQuotes bookStoreQuotes) {
        apply((BookStoreQuotesCreated  event) -> {
            bookStoreQuotes.copies = new ArrayList<>();
            bookStoreQuotes.quotes = new ArrayList<>();
        });
        apply((BookSaved event) -> {
            bookStoreQuotes.copies = new ArrayList<>();
             Copy copy = copyFactory.createCopy(
                            event.getCopyType(),
                            new Title(event.getTitle()),
                            new Author(event.getAuthor()),
                            new Stock(event.getStock()),
                            new PublicationYear(event.getPublicationYear()), new Price(event.getPrice()));
             event.setPrice(copy.getPrice().value());
            bookStoreQuotes.copies.add(copy);
            bookStoreQuotes.setResult(copy);
        });
    }

}

