package com.penguin.usecases.getallcopiesusecase;

import com.penguin.model.bookStoreQuotes.events.BookSaved;
import com.penguin.model.bookStoreQuotes.values.responses.AllCopiesResponse;
import com.penguin.gateways.DomainEventRepository;
import reactor.core.publisher.Flux;

public class GetAllCopiesUseCase {
    private final DomainEventRepository domainEventRepository;

    public GetAllCopiesUseCase(DomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;
    }

    public Flux<AllCopiesResponse> getAllCopies() {
        return domainEventRepository.findAll()
                .filter(event -> event instanceof BookSaved)
                .map(event -> {
                    BookSaved bookSavedEvent = (BookSaved) event;
                    return new AllCopiesResponse(
                            bookSavedEvent.aggregateRootId(),
                            bookSavedEvent.getTitle(),
                            bookSavedEvent.getAuthor(),
                            bookSavedEvent.getStock(),
                            bookSavedEvent.getPublicationYear(),
                            bookSavedEvent.getPrice(),
                            bookSavedEvent.getCopyType(),
                            bookSavedEvent.getUuid()
                    );
                });
    }
}
