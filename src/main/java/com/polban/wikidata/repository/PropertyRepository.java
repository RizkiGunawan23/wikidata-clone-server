package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Property;

public interface PropertyRepository extends Neo4jRepository<Property, String> {
}