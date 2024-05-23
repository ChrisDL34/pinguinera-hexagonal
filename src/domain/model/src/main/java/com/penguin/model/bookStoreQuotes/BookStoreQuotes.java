package com.penguin.model.bookStoreQuotes;

import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.CopyIdAndQuantity;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.Discount;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.GroupPriceResult;
import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePrice;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePriceGroups;
import com.penguin.model.bookStoreQuotes.events.CalculatedPriceWithBudget;
import com.penguin.model.bookStoreQuotes.factory.CopyFactory;
import com.penguin.model.bookStoreQuotes.factory.CopyFactoryImpl;
import com.penguin.model.bookStoreQuotes.values.BookStoreQuotes.*;
import com.penguin.model.bookStoreQuotes.values.copy.*;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.generic.AggregateRoot;
import com.penguin.model.generic.DomainEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class BookStoreQuotes extends AggregateRoot<BookStoreQuoteId> {

    protected Copy copy;
    protected TotalPrice totalPrice;
    protected List<Copy> copies;

    protected Copy result;
    protected List<CalculatedPriceWithBudget.BookDetails> bookDetails;
    CopyFactory copyFactory = new CopyFactoryImpl();
    protected List<GroupPriceResult> groupPriceResults;

    protected TotalPriceWithDiscount totalPriceWithDiscount;
    protected Map<UUID, Integer> recommendations;

    public Map<UUID, Integer> getRecommendations() {
        return recommendations;
    }

    public TotalPriceWithDiscount getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }


    public List<CalculatedPriceWithBudget.BookDetails> getBookDetails() {
        return bookDetails;
    }

    private BookStoreQuotes(BookStoreQuoteId bookStoreQuoteId) {
        super(bookStoreQuoteId);
        this.copies = new ArrayList<>();
    }

    public BookStoreQuotes(Title title, Author author, Stock stock, PublicationYear publicationYear, Price price, Type type) {
        super(new BookStoreQuoteId());
        subscribe(new BookStoreQuotesEventChange(this));
        Copy copy = copyFactory.createCopy(type.value(), title, author, stock, publicationYear, price);
        appendChange(new BookSaved(
                copy.getTitle().value(),
                copy.getAuthor().value(),
                copy.getStock().value(),
                copy.getPublicationYear().value(),
                copy.getPrice().value(),
                type.value())).apply();
    }
    public void addCopy(Title title, Author author, Stock stock, PublicationYear publicationYear, Price price, Type type) {
        Copy copy = copyFactory.createCopy(type.value(), title, author, stock, publicationYear, price);
        appendChange(new BookSaved(
                copy.getTitle().value(),
                copy.getAuthor().value(),
                copy.getStock().value(),
                copy.getPublicationYear().value(),
                copy.getPrice().value(),
                type.value())).apply();
    }
    public Copy getResult() {
        return result;
    }
    public void setResult(Copy result) {
        this.result = result;
    }

    public static BookStoreQuotes from(BookStoreQuoteId bookStoreQuoteId, List<DomainEvent> events, CopyFactory copyFactory) {
        var bookStoreQuotes = new BookStoreQuotes(bookStoreQuoteId);
        bookStoreQuotes.copies = new ArrayList<>();
        bookStoreQuotes.groupPriceResults = new ArrayList<>();
        events.forEach(event -> {
            if (event instanceof BookSaved) {
                BookSaved bookSaved = (BookSaved) event;
                Copy copy = copyFactory.createCopy(
                        bookSaved.getCopyType(),
                        new Title(bookSaved.getTitle()),
                        new Author(bookSaved.getAuthor()),
                        new Stock(bookSaved.getStock()),
                        new PublicationYear(bookSaved.getPublicationYear()),
                        new Price(bookSaved.getPrice())
                );
                bookStoreQuotes.copies.add(copy);
            }
            bookStoreQuotes.applyEvent(event);
        });
        return bookStoreQuotes;
    }
    public static BookStoreQuotes from(BookStoreQuoteId bookStoreQuoteId, List<DomainEvent> events) {
        var bookStoreQuotes = new BookStoreQuotes(bookStoreQuoteId);
        bookStoreQuotes.copies = new ArrayList<>();
        bookStoreQuotes.groupPriceResults = new ArrayList<>();
        CopyFactory copyFactory = new CopyFactoryImpl();
        events.forEach(event -> {
            if (event instanceof BookSaved) {
                BookSaved bookSaved = (BookSaved) event;
                Copy copy = copyFactory.createCopy(
                        bookSaved.getCopyType(),
                        new Title(bookSaved.getTitle()),
                        new Author(bookSaved.getAuthor()),
                        new Stock(bookSaved.getStock()),
                        new PublicationYear(bookSaved.getPublicationYear()),
                        new Price(bookSaved.getPrice())
                );
                bookStoreQuotes.copies.add(copy);
            }
            bookStoreQuotes.applyEvent(event);
        });
        return bookStoreQuotes;
    }
    public Discount calculateDiscount(LocalDate registrationDate, double totalPriceWithDiscount) {
        int years = calculateYearsSinceRegistration(registrationDate);
        double discountPercentage = getDiscountPercentage(years);
        double discountAmount = calculateDiscountAmount(discountPercentage, totalPriceWithDiscount);
        double discountedPrice = totalPriceWithDiscount - discountAmount;
        return new Discount(discountPercentage, discountedPrice);
    }

    private int calculateYearsSinceRegistration(LocalDate registrationDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(registrationDate, currentDate);
        return period.getYears();
    }

    private double getDiscountPercentage(int years) {
        return years < 1 ? 0 : years < 2 ? 12 : 17;
    }
    private double calculateDiscountAmount(double discountPercentage, double totalPriceWithDiscount) {
        return (discountPercentage / 100) * totalPriceWithDiscount;
    }

    public CalculatedMultiplePrice calculateProductPrice(List<CopyIdAndQuantity> copiesIdsQuantity, LocalDate registrationDate, List<DomainEvent> events) {
        double totalPrice = 0;
        for (var copyIdAndQuantity : copiesIdsQuantity) {
            UUID copyEventUUID = copyIdAndQuantity.getId();
            int quantity = copyIdAndQuantity.getQuantity();

            Optional<BookSaved> bookSavedOptional = findBookSavedEvent(events, copyEventUUID);

            if (bookSavedOptional.isPresent()) {
                BookSaved bookSaved = bookSavedOptional.get();
                double price = bookSaved.getPrice();
                List<UnitPrice> unitPrices = calculateUnitPrices(quantity, price);
                updateCopyIdAndQuantity(copyIdAndQuantity, unitPrices, bookSaved);
                double itemTotalPrice = calculateItemTotalPrice(unitPrices);
                totalPrice += itemTotalPrice;
            } else {
                System.err.println("BookSaved event not found with event UUID: " + copyEventUUID);
            }
        }
        double totalPriceWithDiscount = totalPrice;
        Discount discount = calculateDiscount(registrationDate, totalPriceWithDiscount);
        CalculatedMultiplePrice calculatedMultiplePrice = createCalculatedMultiplePrice(copiesIdsQuantity, registrationDate, totalPrice, totalPriceWithDiscount, discount);
        appendChange(calculatedMultiplePrice).apply();
        return calculatedMultiplePrice;
    }

    private Optional<BookSaved> findBookSavedEvent(List<DomainEvent> events, UUID copyEventUUID) {
        return events.stream()
                .filter(event -> event instanceof BookSaved)
                .map(event -> (BookSaved) event)
                .filter(bookSaved -> bookSaved.getUuid().equals(copyEventUUID))
                .findFirst();
    }

    private void updateCopyIdAndQuantity(CopyIdAndQuantity copyIdAndQuantity, List<UnitPrice> unitPrices, BookSaved bookSaved) {
        copyIdAndQuantity.setUnitPrices(unitPrices);
        copyIdAndQuantity.setTitle(bookSaved.getTitle());
        copyIdAndQuantity.setAuthor(bookSaved.getAuthor());
        copyIdAndQuantity.setCopyType(bookSaved.getCopyType());
        copyIdAndQuantity.setTotalPrice(calculateItemTotalPrice(unitPrices));
    }

    private double calculateItemTotalPrice(List<UnitPrice> unitPrices) {
        return unitPrices.stream()
                .mapToDouble(UnitPrice::getPrice)
                .sum();
    }

    private CalculatedMultiplePrice createCalculatedMultiplePrice(List<CopyIdAndQuantity> copiesIdsQuantity, LocalDate registrationDate, double totalPrice, double totalPriceWithDiscount, Discount discount) {
        return new CalculatedMultiplePrice(
                this.identity().value(),
                copiesIdsQuantity,
                registrationDate,
                totalPrice,
                totalPriceWithDiscount,
                discount.getPercentage(),
                discount.getDiscountedPrice()
        );
    }

    private List<UnitPrice> calculateUnitPrices(int quantity, double price) {
        List<UnitPrice> unitPrices = new ArrayList<>();

        for (int i = 1; i <= quantity; i++) {
            double unitPrice = calculateUnitPrice(quantity, price, i);
            unitPrices.add(new UnitPrice(i, Math.round(unitPrice * 100.0) / 100.0));
        }

        return unitPrices;
    }

    private double calculateUnitPrice(int quantity, double price, int i) {
        if (quantity <= 10) {
            return calculateUnitPriceForSmallQuantity(price, i);
        } else {
            return calculateUnitPriceForLargeQuantity(price, i);
        }
    }

    private double calculateUnitPriceForSmallQuantity(double price, int i) {
        return price * Math.pow(1.02, i - 1);
    }

    private double calculateUnitPriceForLargeQuantity(double price, int i) {
        if (i <= 10) {
            return price;
        } else {
            return calculateUnitPriceWithDiscount(price, i);
        }
    }

    private double calculateUnitPriceWithDiscount(double price, int i) {
        return price * (1 - 0.0015 * (i - 10));
    }


    public CalculatedPriceWithBudget calculatePriceWithBudget(List<String> copiesIds, LocalDate registrationDate, BigDecimal budget, List<DomainEvent> events) {
        Map<UUID, Integer> recommendations = new HashMap<>();
        List<CalculatedPriceWithBudget.BookDetails> bookDetails = new ArrayList<>();
        this.recommendations = recommendations;

        List<BookSaved> books = getFilteredBooks(events, copiesIds);
        validateBooks(books);

        BookSaved novelBook = findBookByType(books, "Novel");
        BookSaved otherBook = findBookByType(books, "Book");
        validateBookTypes(novelBook, otherBook);

        BookSaved cheaperBook = getCheaperBook(novelBook, otherBook);
        BookSaved moreExpensiveBook = getMoreExpensiveBook(novelBook, otherBook);

        BigDecimal totalPrice = calculateTotalPriceAndUpdateBudget(budget, cheaperBook, moreExpensiveBook, recommendations, bookDetails);

        BigDecimal totalPriceWithDiscount = calculateTotalPriceWithDiscount(totalPrice);
        this.totalPriceWithDiscount = new TotalPriceWithDiscount(totalPriceWithDiscount.doubleValue());

        CalculatedPriceWithBudget calculatedPriceWithBudget = createCalculatedPriceWithBudget(copiesIds, registrationDate, budget, recommendations, totalPriceWithDiscount, bookDetails);
        appendChange(calculatedPriceWithBudget).apply();

        return calculatedPriceWithBudget;
    }

    private BigDecimal calculateTotalPriceAndUpdateBudget(BigDecimal budget, BookSaved cheaperBook,
                                                          BookSaved moreExpensiveBook, Map<UUID, Integer>
                                                                  recommendations, List<CalculatedPriceWithBudget.BookDetails> bookDetails) {
        BigDecimal cheaperPrice = BigDecimal.valueOf(cheaperBook.getPrice());
        BigDecimal moreExpensivePrice = BigDecimal.valueOf(moreExpensiveBook.getPrice());

        int cheaperQuantity = calculateCheaperQuantity(budget, moreExpensivePrice, cheaperPrice);
        budget = updateBudget(budget, cheaperPrice, cheaperQuantity);

        int moreExpensiveQuantity = calculateMoreExpensiveQuantity(budget, moreExpensivePrice);
        budget = updateBudgetForMoreExpensiveBook(budget, moreExpensivePrice, moreExpensiveQuantity);

        BigDecimal totalPrice = calculateTotalPrice(cheaperPrice, cheaperQuantity, moreExpensivePrice, moreExpensiveQuantity);

        updateRecommendations(recommendations, cheaperBook, moreExpensiveBook, cheaperQuantity, moreExpensiveQuantity);
        updateBookDetails(bookDetails, cheaperBook, moreExpensiveBook, cheaperQuantity, moreExpensiveQuantity);
        this.bookDetails = bookDetails;

        return totalPrice;
    }

    private BigDecimal updateBudgetForMoreExpensiveBook(BigDecimal budget, BigDecimal moreExpensivePrice, int moreExpensiveQuantity) {
        if (moreExpensiveQuantity > 0) {
            budget = budget.subtract(moreExpensivePrice);
        }
        return budget;
    }

    private BigDecimal calculateTotalPrice(BigDecimal cheaperPrice, int cheaperQuantity, BigDecimal moreExpensivePrice, int moreExpensiveQuantity) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = totalPrice.add(cheaperPrice.multiply(BigDecimal.valueOf(cheaperQuantity)));
        if (moreExpensiveQuantity > 0) {
            totalPrice = totalPrice.add(moreExpensivePrice);
        }
        return totalPrice;
    }

    private List<BookSaved> getFilteredBooks(List<DomainEvent> events, List<String> copiesIds) {
        return events.stream()
                .filter(event -> event instanceof BookSaved)
                .map(event -> (BookSaved) event)
                .filter(bookSaved -> copiesIds.contains(bookSaved.getUuid().toString()))
                .collect(Collectors.toList());
    }

    private void validateBooks(List<BookSaved> books) {
        if (books.size() != 2) {
            throw new IllegalArgumentException("Invalid number of books provided: " + books.size());
        }
    }

    private BookSaved findBookByType(List<BookSaved> books, String type) {
        return books.stream().filter(book -> type.equals(book.getCopyType())).findFirst().orElse(null);
    }

    private void validateBookTypes(BookSaved novelBook, BookSaved otherBook) {
        if (novelBook == null || otherBook == null) {
            throw new IllegalArgumentException("Books must include one novel and one book.");
        }
    }

    private BookSaved getCheaperBook(BookSaved novelBook, BookSaved otherBook) {
        return novelBook.getPrice() < otherBook.getPrice() ? novelBook : otherBook;
    }

    private BookSaved getMoreExpensiveBook(BookSaved novelBook, BookSaved otherBook) {
        return novelBook.getPrice() >= otherBook.getPrice() ? novelBook : otherBook;
    }

    private int calculateCheaperQuantity(BigDecimal budget, BigDecimal moreExpensivePrice, BigDecimal cheaperPrice) {
        int cheaperQuantity = budget.subtract(moreExpensivePrice).divide(cheaperPrice, RoundingMode.FLOOR).intValue();
        return Math.max(cheaperQuantity, 0);
    }

    private BigDecimal updateBudget(BigDecimal budget, BigDecimal cheaperPrice, int cheaperQuantity) {
        return budget.subtract(cheaperPrice.multiply(BigDecimal.valueOf(cheaperQuantity)));
    }

    private int calculateMoreExpensiveQuantity(BigDecimal budget, BigDecimal moreExpensivePrice) {
        return budget.compareTo(moreExpensivePrice) >= 0 ? 1 : 0;
    }

    private void updateRecommendations(Map<UUID, Integer> recommendations, BookSaved cheaperBook, BookSaved moreExpensiveBook, int cheaperQuantity, int moreExpensiveQuantity) {
        recommendations.put(cheaperBook.getUuid(), cheaperQuantity);
        recommendations.put(moreExpensiveBook.getUuid(), moreExpensiveQuantity);
    }

    private void updateBookDetails(List<CalculatedPriceWithBudget.BookDetails> bookDetails, BookSaved cheaperBook, BookSaved moreExpensiveBook, int cheaperQuantity, int moreExpensiveQuantity) {
        bookDetails.add(new CalculatedPriceWithBudget.BookDetails(cheaperBook.getUuid(), cheaperBook.getTitle(), cheaperBook.getCopyType(), cheaperBook.getAuthor(), cheaperQuantity));
        bookDetails.add(new CalculatedPriceWithBudget.BookDetails(moreExpensiveBook.getUuid(), moreExpensiveBook.getTitle(), moreExpensiveBook.getCopyType(), moreExpensiveBook.getAuthor(), moreExpensiveQuantity));
    }

    private BigDecimal calculateTotalPriceWithDiscount(BigDecimal totalPrice) {
        return totalPrice.multiply(BigDecimal.valueOf(0.83));
    }

    private CalculatedPriceWithBudget createCalculatedPriceWithBudget(List<String> copiesIds, LocalDate registrationDate, BigDecimal budget, Map<UUID, Integer> recommendations, BigDecimal totalPriceWithDiscount, List<CalculatedPriceWithBudget.BookDetails> bookDetails) {
        return new CalculatedPriceWithBudget(
                this.identity().value(),
                copiesIds,
                registrationDate,
                budget.doubleValue(),
                recommendations,
                totalPriceWithDiscount.doubleValue(),
                bookDetails
        );
    }

    public CalculatedMultiplePriceGroups calculateProductPriceGroups(List<List<CopyIdAndQuantity>> copiesIdsQuantityGroups, LocalDate registrationDate, List<DomainEvent> events) {
        List<GroupPriceResult> groupPriceResults = new ArrayList<>();

        for (List<CopyIdAndQuantity> copiesIdsQuantityGroup : copiesIdsQuantityGroups) {
            GroupPriceResult groupPriceResult = calculateGroupPriceResult(copiesIdsQuantityGroup, events);
            groupPriceResults.add(groupPriceResult);
        }

        CalculatedMultiplePriceGroups calculatedMultiplePriceGroups = createCalculatedMultiplePriceGroups(groupPriceResults, registrationDate);
        appendChange(calculatedMultiplePriceGroups).apply();

        return calculatedMultiplePriceGroups;
    }

    private GroupPriceResult calculateGroupPriceResult(List<CopyIdAndQuantity> copiesIdsQuantityGroup, List<DomainEvent> events) {
        double totalPrice = 0;
        int totalQuantity = 0;

        for (CopyIdAndQuantity copyIdAndQuantity : copiesIdsQuantityGroup) {
            BookSaved bookSaved = findBookSavedEvent(copyIdAndQuantity.getId(), events);
            int quantity = copyIdAndQuantity.getQuantity();
            double price = bookSaved.getPrice();

            List<UnitPrice> unitPrices = calculateUnitPrices(quantity, price);
            double itemTotalPrice = calculateTotalPriceForItem(unitPrices);

            updateCopyIdAndQuantity(copyIdAndQuantity, unitPrices, bookSaved, itemTotalPrice);

            totalPrice += itemTotalPrice;
            totalQuantity += quantity;
        }

        double totalPriceWithDiscount = calculateTotalPriceWithDiscount(totalPrice, totalQuantity);
        double formattedTotalPrice = formatPrice(totalPrice);
        double formattedTotalPriceWithDiscount = formatPrice(totalPriceWithDiscount);

        return new GroupPriceResult(copiesIdsQuantityGroup, formattedTotalPrice, formattedTotalPriceWithDiscount);
    }

    private BookSaved findBookSavedEvent(UUID copyEventUUID, List<DomainEvent> events) {
        return events.stream()
                .filter(event -> event instanceof BookSaved)
                .map(event -> (BookSaved) event)
                .filter(bookSaved -> bookSaved.getUuid().equals(copyEventUUID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("BookSaved event not found for copyEventUUID: " + copyEventUUID));
    }

    private double calculateTotalPriceForItem(List<UnitPrice> unitPrices) {
        return unitPrices.stream()
                .mapToDouble(UnitPrice::getPrice)
                .sum();
    }

    private void updateCopyIdAndQuantity(CopyIdAndQuantity copyIdAndQuantity, List<UnitPrice> unitPrices, BookSaved bookSaved, double itemTotalPrice) {
        copyIdAndQuantity.setUnitPrices(unitPrices);
        copyIdAndQuantity.setTitle(bookSaved.getTitle());
        copyIdAndQuantity.setAuthor(bookSaved.getAuthor());
        copyIdAndQuantity.setCopyType(bookSaved.getCopyType());
        copyIdAndQuantity.setTotalPrice(itemTotalPrice);
    }

    private double calculateTotalPriceWithDiscount(double totalPrice, int totalQuantity) {
        return totalQuantity < 10 ? totalPrice * 1.02 : totalPrice * 0.9985;
    }

    private double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    private CalculatedMultiplePriceGroups createCalculatedMultiplePriceGroups(List<GroupPriceResult> groupPriceResults, LocalDate registrationDate) {
        return new CalculatedMultiplePriceGroups(
                this.identity().value(),
                groupPriceResults,
                registrationDate
        );
    }

    public List<Copy> getCopies() {
        return copies;
    }
}