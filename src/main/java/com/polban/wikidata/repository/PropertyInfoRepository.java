package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.PropertyInfo;

public interface PropertyInfoRepository extends Neo4jRepository<PropertyInfo, String> {
}