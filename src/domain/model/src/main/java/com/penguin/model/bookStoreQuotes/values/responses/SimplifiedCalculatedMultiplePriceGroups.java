package com.penguin.model.bookStoreQuotes.values.responses;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.GroupPriceResult;

import java.time.LocalDate;
import java.util.List;

public class SimplifiedCalculatedMultiplePriceGroups {
    private List<GroupPriceResult> groupPriceResults;
    private LocalDate registrationDate;

    public SimplifiedCalculatedMultiplePriceGroups() {
    }

    public SimplifiedCalculatedMultiplePriceGroups(List<GroupPriceResult> groupPriceResults, LocalDate registrationDate) {
        this.groupPriceResults = groupPriceResults;
        this.registrationDate = registrationDate;
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