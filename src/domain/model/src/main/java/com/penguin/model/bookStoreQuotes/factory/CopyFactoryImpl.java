package com.penguin.model.bookStoreQuotes.factory;

import com.penguin.model.bookStoreQuotes.entity.Book;
import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.entity.Novel;
import com.penguin.model.bookStoreQuotes.values.copy.*;

public class CopyFactoryImpl implements CopyFactory {
    @Override
    public Copy createCopy(String type, Title title, Author author, Stock stock, PublicationYear publicationYear, Price price) {
        System.out.println("Type en CopyFactoryImpl: " + type);

        if ("Book".equals(type)) {
            return Book.from(title, author, stock, publicationYear, price);
        } else if ("Novel".equals(type)) {
            return Novel.from(title, author, stock, publicationYear, price);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }
}
