package com.polban.wikidata.config;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jConfig implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Neo4jConfig.class);

    @Autowired
    private Driver driver;

    public void run(String... args) throws Exception {
        try (Session session = driver.session()) {
            session.run("CREATE CONSTRAINT username_unique IF NOT EXISTS FOR (u:User) REQUIRE u.username IS UNIQUE");
            session.run("CREATE CONSTRAINT email_unique IF NOT EXISTS FOR (u:User) REQUIRE u.email IS UNIQUE");

            logger.info("Neo4j constraints created successfully.");
        } catch (Exception e) {
            logger.error("Error creating Neo4j constraints: {}", e.getMessage());
        }
    }
}
