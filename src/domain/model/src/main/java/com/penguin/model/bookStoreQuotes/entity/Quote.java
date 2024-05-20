package com.penguin.model.bookStoreQuotes.entity;

import com.penguin.model.bookStoreQuotes.values.identities.CustomerId;
import com.penguin.model.bookStoreQuotes.values.identities.QuoteId;
import com.penguin.model.bookStoreQuotes.values.quote.Discount;
import com.penguin.model.bookStoreQuotes.values.quote.Total;
import com.penguin.model.generic.Entity;

import java.util.List;

public class Quote extends Entity<QuoteId> {

    private  CustomerId customerId;
    private  List<Copy> copies;
    private  Total total;
    private  Discount discount;

    public Quote(CustomerId customerId, List<Copy> copies, String author, Total total, Discount discount) {
        super(new QuoteId());
        this.customerId = customerId;
        this.copies = copies;
        this.total = total;
        this.discount = discount;
    }

    public Quote(QuoteId id) {
        super(id);
    }

    public static Quote from(
            CustomerId customerId,
            List<Copy> copies,
            String author,
            Total total,
            Discount discount
    ){
        return new Quote(customerId, copies, author, total, discount);
    }

    public void applyWholeSalePurchaseIncrement(){}

    public void applyRetailSalePurchaseIncrement(){}

    public void appyDiscount(){}

}
