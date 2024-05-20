package com.penguin.model.bookStoreQuotes.commands;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;
import com.penguin.model.generic.Command;

import java.time.LocalDate;
import java.util.List;

public class CalculateMultiplePriceGroupsCommand extends Command {
    private String bookStoreQuotesId;
    private List<List<CopyIdAndQuantity>> copiesIdsQuantityGroups;
    private LocalDate registrationDate;

    public CalculateMultiplePriceGroupsCommand(String bookStoreQuotesId, List<List<CopyIdAndQuantity>> copiesIdsQuantityGroups,
                                               LocalDate registrationDate) {
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIdsQuantityGroups = copiesIdsQuantityGroups;
        this.registrationDate = registrationDate;
    }

    public CalculateMultiplePriceGroupsCommand() {

    }


    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public List<List<CopyIdAndQuantity>> getCopiesIdsQuantityGroups() {
        return copiesIdsQuantityGroups;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}