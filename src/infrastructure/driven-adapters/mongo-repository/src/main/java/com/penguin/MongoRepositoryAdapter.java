package com.penguin;

import com.penguin.data.Event;
import com.penguin.gateways.DomainEventRepository;
import com.penguin.model.generic.DomainEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Repository
public class MongoRepositoryAdapter implements DomainEventRepository {

    private final ReactiveMongoTemplate template;
    private final JSONMapper eventSerializer;

    public MongoRepositoryAdapter(ReactiveMongoTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        Event eventStore = new Event();
        eventStore.setAggregateRootId(event.aggregateRootId());
        eventStore.setEventBody(eventStore.mapperEventBody(event));
        eventStore.setOccurredOn(new Date());
        eventStore.setTypeName(event.getClass().getTypeName());
        return template.save(eventStore)
                .map(eventStored -> event);
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        System.out.println(aggregateId);
        return template.find(new Query(Criteria.where("aggregateRootId").is(aggregateId)), Event.class)
                .sort(Comparator.comparing(Event::getOccurredOn))
                .map(storedEvent -> {
                    System.out.println(storedEvent.getEventBody());
                    return storedEvent.deserializeEvent(eventSerializer);
                });
    }


    @Override
    public Flux<DomainEvent> findAll() {
        return template.findAll(Event.class).map(storedEvent -> storedEvent.deserializeEvent(eventSerializer));
    }


}
