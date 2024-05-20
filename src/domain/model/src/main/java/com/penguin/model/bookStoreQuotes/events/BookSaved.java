package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.events.enums.TypeEvent;
import com.penguin.model.generic.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class BookSaved extends DomainEvent {

    private String bookstoreQuoteId;
    private String title;
    private String author;
    private Integer stock;
    private Integer publicationYear;
    private Double price;
    private String copyType;

    public BookSaved() {

    }
    public BookSaved(String title, String author, Integer stock, Integer publicationYear, Double price, String type) {
        super(TypeEvent.BookSaved.toString());
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.publicationYear = publicationYear;
        this.price = price;
        this.copyType = type;
    }



    public BookSaved(Instant when, UUID uuid, String type, String aggregateRootId, String aggregate, Long versionType, String bookstoreQuoteId, String title, String author, Integer stock, Integer publicationYear, Double price) {
        super(when, uuid, type, aggregateRootId, aggregate, versionType);
        this.bookstoreQuoteId = bookstoreQuoteId;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.publicationYear = publicationYear;
        this.price = price;
        this.copyType = type;
    }

    public UUID getUuid() {
        return uuid;
    }
    public String getBookstoreQuoteId() {
        return bookstoreQuoteId;
    }

    public void setBookstoreQuoteId(String bookstoreQuoteId) {
        this.bookstoreQuoteId = bookstoreQuoteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCopyType() {
        return copyType;
    }

    public void setCopyType(String copyType) {
        this.copyType = copyType;
    }
}
