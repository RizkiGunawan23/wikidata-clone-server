package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Qualifier;

public interface QualifierRepository extends Neo4jRepository<Qualifier, String> {
}