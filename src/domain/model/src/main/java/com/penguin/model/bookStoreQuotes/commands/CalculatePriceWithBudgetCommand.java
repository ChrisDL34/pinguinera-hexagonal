package com.penguin.model.bookStoreQuotes.commands;

import com.penguin.model.generic.Command;

import java.time.LocalDate;
import java.util.List;

public class CalculatePriceWithBudgetCommand extends Command {

    private String bookStoreQuotesId;
    private List<String> copiesIds;
    private LocalDate registrationDate;
    private double budget;

    public CalculatePriceWithBudgetCommand(String bookStoreQuotesId, List<String> copiesIds,
                                           LocalDate registrationDate, double budget) {
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIds = copiesIds;
        this.registrationDate = registrationDate;
        this.budget = budget;
    }

    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public List<String> getCopiesIds() {
        return copiesIds;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public double getBudget() {
        return budget;
    }
}
