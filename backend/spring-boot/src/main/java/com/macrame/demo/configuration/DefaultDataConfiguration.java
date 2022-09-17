package com.macrame.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableJpaRepositories(basePackages = {"com.macrame.demo.repositories.jpa"})
@EnableReactiveMongoRepositories(basePackages = {"com.macrame.demo.repositories.mongo"})
@Configuration
public class DefaultDataConfiguration {
}
