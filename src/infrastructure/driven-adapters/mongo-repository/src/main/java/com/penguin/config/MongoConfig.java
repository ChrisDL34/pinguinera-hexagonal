package com.penguin.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;


@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String uri) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleReactiveMongoDatabaseFactory(mongoClient, "bookStore");
    }
}