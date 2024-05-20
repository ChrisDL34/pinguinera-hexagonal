//package com.penguin.model.bookStoreQuotes.entity;
//
//import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.TotalIncrement;
//import com.penguin.model.bookStoreQuotes.values.copy.*;
//import com.penguin.model.bookStoreQuotes.values.identities.CopyId;
//import com.penguin.model.generic.DomainEvent;
//import com.penguin.model.generic.Entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class Copy extends Entity<CopyId> {
//
//    private final Title title;
//    private final Author author;
//    private final Stock stock;
//    private final PublicationYear publicationYear;
//    private Price price;
//    private Type type;
//    private TotalIncrement increment;
//    private DiscountMayor discountMayor;
//    private DiscountCustomer discountCustomer;
//
//    // implementacion
//    public Copy() {
//        super(new CopyId());
//        this.title = null;
//        this.author = null;
//        this.stock = null;
//        this.publicationYear = null;
//        this.price = null;
//        this.type = null;
//    }//
//
//    public Copy(Title title, Author author, Stock stock, Price price, PublicationYear publicationYear) {
//        super(new CopyId());
//        this.title = title;
//        this.author = author;
//        this.stock = stock;
//        this.price = price;
//        this.publicationYear = publicationYear;
//    }
//
//
//    public abstract void calculateIndividualPrice();
//    public List<DomainEvent> getDomainEvents() {
//        return new ArrayList<>();
//    }
//
//    public void applyDiscount(){}
//
//    public void appplyIncrementPrice(){}
//
//    public Price getPrice() {
//        return price;
//    }
//
//    public void setPrice(Price price) {
//        this.price = price;
//    }
//
//    public Title getTitle() {
//        return title;
//    }
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public Stock getStock() {
//        return stock;
//    }
//
//    public PublicationYear getPublicationYear() {
//        return publicationYear;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public TotalIncrement getIncrement() {
//        return increment;
//    }
//
//    public void setIncrement(TotalIncrement increment) {
//        this.increment = increment;
//    }
//
//    public DiscountMayor getDiscountMayor() {
//        return discountMayor;
//    }
//
//    public void setDiscountMayor(DiscountMayor discountMayor) {
//        this.discountMayor = discountMayor;
//    }
//
//    public DiscountCustomer getDiscountCustomer() {
//        return discountCustomer;
//    }
//
//    public void setDiscountCustomer(DiscountCustomer discountCustomer) {
//        this.discountCustomer = discountCustomer;
//    }
//
//    public CopyId identity(CopyId id) {
//        return CopyId.of(id.toString());
//    }
//}


package com.penguin.model.bookStoreQuotes.entity;

import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.TotalIncrement;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.CopyId;
import com.penguin.model.generic.DomainEvent;
import com.penguin.model.generic.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Copy extends Entity<CopyId> {

    private  Title title;
    private  Author author;
    private  Stock stock;
    private  PublicationYear publicationYear;
    private Price price;
    private Type type;
    private TotalIncrement increment;
    private DiscountMayor discountMayor;
    private DiscountCustomer discountCustomer;

//    public Copy() {
//        super(new CopyId());
//        this.title = null;
//        this.author = null;
//        this.stock = null;
//        this.publicationYear = null;
//        this.price = null;
//        this.type = null;
//    }

    public Copy(CopyId id) {
        super(id);
    }

    public Copy(Title title, Author author, Stock stock, Price price, PublicationYear publicationYear) {
        super(new CopyId());
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
        this.publicationYear = publicationYear;
    }

    public abstract void calculateIndividualPrice();

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>();
    }

    public void applyDiscount() {}

    public void applyIncrementPrice() {}

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Stock getStock() {
        return stock;
    }

    public PublicationYear getPublicationYear() {
        return publicationYear;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TotalIncrement getIncrement() {
        return increment;
    }

    public void setIncrement(TotalIncrement increment) {
        this.increment = increment;
    }

    public DiscountMayor getDiscountMayor() {
        return discountMayor;
    }

    public void setDiscountMayor(DiscountMayor discountMayor) {
        this.discountMayor = discountMayor;
    }

    public DiscountCustomer getDiscountCustomer() {
        return discountCustomer;
    }

    public void setDiscountCustomer(DiscountCustomer discountCustomer) {
        this.discountCustomer = discountCustomer;
    }

    public CopyId identity(CopyId id) {
        return CopyId.of(id.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copy copy = (Copy) o;
        return Objects.equals(identity(), copy.identity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity());
    }
}
