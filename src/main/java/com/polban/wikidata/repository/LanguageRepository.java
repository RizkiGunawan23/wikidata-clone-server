package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.Language;

public interface LanguageRepository extends Neo4jRepository<Language, String> {
}