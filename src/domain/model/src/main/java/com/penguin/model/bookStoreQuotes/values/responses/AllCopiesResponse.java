package com.penguin.model.bookStoreQuotes.values.responses;

import java.util.UUID;

public class AllCopiesResponse {
    private String bookId;
    private String title;
    private String author;
    private int stock;
    private int publicationYear;
    private double price;
    private String copyType;
    private UUID uuid;

    public AllCopiesResponse() {
    }

    public AllCopiesResponse(String bookId, String title, String author, int stock, int publicationYear, double price, String copyType) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.publicationYear = publicationYear;
        this.price = price;
        this.copyType = copyType;
    }

    public AllCopiesResponse(String bookId, String title, String author, int stock, int publicationYear, double price, String copyType, UUID uuid) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.publicationYear = publicationYear;
        this.price = price;
        this.copyType = copyType;
        this.uuid = uuid;
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCopyType() {
        return copyType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCopyType(String copyType) {
        this.copyType = copyType;
    }
}
