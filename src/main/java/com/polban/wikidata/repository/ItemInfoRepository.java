package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polban.wikidata.model.ItemInfo;

public interface ItemInfoRepository extends Neo4jRepository<ItemInfo, String> {
}