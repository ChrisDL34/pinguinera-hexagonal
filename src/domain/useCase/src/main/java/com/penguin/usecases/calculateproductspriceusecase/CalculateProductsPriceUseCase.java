package com.penguin.usecases.calculateproductspriceusecase;

import com.penguin.gateways.DomainEventRepository;
import com.penguin.generic.CommandUseCase;
import com.penguin.model.bookStoreQuotes.BookStoreQuotes;
import com.penguin.model.bookStoreQuotes.commands.CalculateMultiplePriceCommand;
import com.penguin.model.bookStoreQuotes.events.CalculatedMultiplePrice;
import com.penguin.model.bookStoreQuotes.values.identities.BookStoreQuoteId;
import com.penguin.model.bookStoreQuotes.values.responses.SimplifiedCalculatedMultiplePrice;
import com.penguin.model.generic.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CalculateProductsPriceUseCase extends CommandUseCase<CalculateMultiplePriceCommand> {
    private final DomainEventRepository repository;

    @Autowired
    public CalculateProductsPriceUseCase(DomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CalculateMultiplePriceCommand> calculateMultiplePriceCommandMono) {
        return calculateMultiplePriceCommandMono.flatMapMany(command ->
                repository.findById(command.getBookStoreQuotesId())
                        .collectList()
                        .flatMapMany(events -> {
                            BookStoreQuoteId bookStoreQuoteId = BookStoreQuoteId.of(command.getBookStoreQuotesId());
                            BookStoreQuotes bookStoreQuotes = BookStoreQuotes.from(bookStoreQuoteId, events);
                            CalculatedMultiplePrice calculatedMultiplePrice = bookStoreQuotes.calculateProductPrice(
                                    command.getCopiesIdsQuantity(),
                                    command.getRegistrationDate(),
                                    events
                            );

                            return repository.saveEvent(calculatedMultiplePrice)
                                    .thenMany(Flux.fromIterable(bookStoreQuotes.getUncommittedChanges()));
                        }));
    }

    public Mono<SimplifiedCalculatedMultiplePrice> calculateAndSimplify(Mono<CalculateMultiplePriceCommand> calculateMultiplePriceCommandMono) {
        return apply(calculateMultiplePriceCommandMono)
                .filter(event -> event instanceof CalculatedMultiplePrice)
                .map(event -> (CalculatedMultiplePrice) event)
                .map(this::toSimplifiedCalculatedMultiplePrice)
                .next();
    }

    private SimplifiedCalculatedMultiplePrice toSimplifiedCalculatedMultiplePrice(CalculatedMultiplePrice event) {
        SimplifiedCalculatedMultiplePrice simplified = new SimplifiedCalculatedMultiplePrice();
        simplified.setCopiesIdsQuantity(event.getCopiesIdsQuantity());
        simplified.setRegistrationDate(event.getRegistrationDate());
        simplified.setTotalPrice(event.getTotalPrice());
        simplified.setTotalPriceWithDiscount(event.getTotalPriceWithDiscount());
        simplified.setDiscountPercentage(event.getDiscountPercentage());
        simplified.setDiscountedPrice(event.getDiscountedPrice());
        return simplified;
    }
}
