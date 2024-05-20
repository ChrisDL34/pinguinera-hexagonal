package com.penguin.model.bookStoreQuotes;

import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePrice;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePriceGroups;
import com.penguin.model.bookStoreQuotes.events.CalculatedPriceWithBudget;
import com.penguin.model.bookStoreQuotes.factory.CopyFactory;
import com.penguin.model.bookStoreQuotes.factory.CopyFactoryImpl;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.TotalPrice;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.generic.EventChange;

import java.util.ArrayList;

public class BookStoreQuotesEventChange extends EventChange {

    private final CopyFactory copyFactory = new CopyFactoryImpl();

    public BookStoreQuotesEventChange(BookStoreQuotes bookStoreQuotes) {

        apply((BookSaved event) -> {
            Copy copy = copyFactory.createCopy(
                    event.getCopyType(),
                    new Title(event.getTitle()),
                    new Author(event.getAuthor()),
                    new Stock(event.getStock()),
                    new PublicationYear(event.getPublicationYear()),
                    new Price(event.getPrice()));
            copy.calculateIndividualPrice();
            bookStoreQuotes.copies.add(copy);
            bookStoreQuotes.setResult(copy);
        });

        apply((CalculatedMultiplePrice event) -> {
            bookStoreQuotes.totalPrice = new TotalPrice(event.getTotalPrice());
            bookStoreQuotes.totalPriceWithDiscount = new TotalPriceWithDiscount(event.getTotalPriceWithDiscount());
        });

        apply((CalculatedPriceWithBudget event) -> {
            bookStoreQuotes.recommendations = event.getRecommendations();
            bookStoreQuotes.totalPriceWithDiscount = new TotalPriceWithDiscount(event.getTotalPriceWithDiscount());
        });

        apply((CalculatedMultiplePriceGroups event) -> {
            bookStoreQuotes.groupPriceResults = event.getGroupPriceResults();
        });
    }
}
