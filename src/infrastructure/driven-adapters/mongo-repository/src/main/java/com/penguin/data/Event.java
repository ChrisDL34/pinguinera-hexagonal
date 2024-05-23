package com.penguin.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.penguin.JSONMapper;
import com.penguin.model.generic.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

@Setter
@Getter
@Document
public class Event {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private String aggregateRootId;
    private String eventBody;
    private Date occurredOn;
    private String typeName;

    public String mapperEventBody(DomainEvent event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException | ClassCastException e) {
            return "Error writing to JSON";
        }
    }
    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer){
        return eventSerializer.writeToJson(domainEvent);
    }
    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
        try {
            System.out.println("Event Body: " + this.getEventBody());
            return (DomainEvent) eventSerializer
                    .readFromJson(this.getEventBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
