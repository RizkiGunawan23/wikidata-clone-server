package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.ReferenceInfo;

public interface ReferenceInfoRepository extends Neo4jRepository<ReferenceInfo, String> {
}