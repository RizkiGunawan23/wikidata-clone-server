package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Reference;

public interface ReferenceRepository extends Neo4jRepository<Reference, String> {
}