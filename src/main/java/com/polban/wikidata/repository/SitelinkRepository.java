package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Sitelink;

public interface SitelinkRepository extends Neo4jRepository<Sitelink, String> {
}