package com.penguin.model.bookStoreQuotes.events;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.GroupPriceResult;
import com.penguin.model.bookStoreQuotes.events.enums.TypeEvent;
import com.penguin.model.generic.DomainEvent;

import java.time.LocalDate;
import java.util.List;

public class CalculatedMultiplePriceGroups extends DomainEvent {
    private String bookStoreQuotesId;
    private List<GroupPriceResult> groupPriceResults;
    private LocalDate registrationDate;

    public CalculatedMultiplePriceGroups(String bookStoreQuotesId, List<GroupPriceResult> groupPriceResults,
                                         LocalDate registrationDate) {
        super(TypeEvent.CalculatedMultiplePriceGroups.toString());
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.groupPriceResults = groupPriceResults;
        this.registrationDate = registrationDate;
    }

    public CalculatedMultiplePriceGroups() {

    }

    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public void setBookStoreQuotesId(String bookStoreQuotesId) {
        this.bookStoreQuotesId = bookStoreQuotesId;
    }

    public List<GroupPriceResult> getGroupPriceResults() {
        return groupPriceResults;
    }

    public void setGroupPriceResults(List<GroupPriceResult> groupPriceResults) {
        this.groupPriceResults = groupPriceResults;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}