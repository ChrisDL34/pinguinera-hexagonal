package com.penguin.generic;

import com.penguin.model.generic.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public interface EventBus {

    void publish(DomainEvent event);
}
