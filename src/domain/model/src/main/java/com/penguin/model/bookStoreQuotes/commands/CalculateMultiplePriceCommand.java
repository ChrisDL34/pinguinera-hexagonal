package com.penguin.model.bookStoreQuotes.commands;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;
import com.penguin.model.generic.Command;

import java.time.LocalDate;
import java.util.List;

public class CalculateMultiplePriceCommand extends Command {

    private String bookStoreQuotesId;
    private List<CopyIdAndQuantity> copiesIdsQuantity;
    private LocalDate registrationDate;

    public CalculateMultiplePriceCommand(String bookStoreQuotesId, List<CopyIdAndQuantity> copiesIdsQuantity,
                                         LocalDate registrationDate) {
        this.bookStoreQuotesId = bookStoreQuotesId;
        this.copiesIdsQuantity = copiesIdsQuantity;
        this.registrationDate = registrationDate;
    }

    public String getBookStoreQuotesId() {
        return bookStoreQuotesId;
    }

    public List<CopyIdAndQuantity> getCopiesIdsQuantity() {
        return copiesIdsQuantity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}
