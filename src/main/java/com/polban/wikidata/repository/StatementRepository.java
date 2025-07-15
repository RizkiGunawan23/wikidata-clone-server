package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Statement;

public interface StatementRepository extends Neo4jRepository<Statement, String> {
}