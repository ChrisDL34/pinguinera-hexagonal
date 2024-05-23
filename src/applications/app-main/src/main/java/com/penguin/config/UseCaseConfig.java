package com.penguin.config;

import com.penguin.MongoRepositoryAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.penguin"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+Handler$"),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MongoRepositoryAdapter.class})
        },
        useDefaultFilters = false)
public class UseCaseConfig {
}
