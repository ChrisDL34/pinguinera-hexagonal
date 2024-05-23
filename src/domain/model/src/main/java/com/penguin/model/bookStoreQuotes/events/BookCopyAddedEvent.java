package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.events.enums.TypeEvent;
import com.penguin.model.generic.DomainEvent;

public class BookCopyAddedEvent extends DomainEvent {
    private String bookId;
    private String title;
    private String author;
    private int stock;
    private int publicationYear;
    private double price;
    private String type;

    public BookCopyAddedEvent(String bookId, String title, String author, int stock, int publicationYear, double price, String type) {
        super(TypeEvent.BookCopyAddedEvent.toString());
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.publicationYear = publicationYear;
        this.price = price;
        this.type = type;
    }

    public BookCopyAddedEvent() {
    }


    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }
    public int getPublicationYear() { return publicationYear; }
    public double getPrice() { return price; }
    public String getType() { return type; }
}
