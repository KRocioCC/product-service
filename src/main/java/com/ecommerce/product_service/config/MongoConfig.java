package com.ecommerce.product_service.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    // Inyectamos las variables.
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.authentication-database}")
    private String authDatabase;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        // Usamos las variables inyectadas
        MongoCredential credential = MongoCredential.createCredential(
                username, authDatabase, password.toCharArray()
        );

        // string format para armar la URL dinamicamente
        String connectionString = String.format("mongodb://%s:%d", host, port);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .credential(credential)
                .build();

        return MongoClients.create(settings);
    }
}
