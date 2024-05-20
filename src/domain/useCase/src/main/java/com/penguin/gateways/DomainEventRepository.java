package com.penguin.gateways;

import com.penguin.model.generic.DomainEvent;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface DomainEventRepository {

    Mono<DomainEvent> saveEvent(DomainEvent event);
    Flux<DomainEvent> findById(String aggregateId);

    Flux<DomainEvent> findAll();
}
