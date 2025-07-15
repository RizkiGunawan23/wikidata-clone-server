package com.polban.wikidata.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.polban.wikidata.model.Item;

public interface ItemRepository extends Neo4jRepository<Item, String> {
    @Query("MATCH (ki:Item) " +
            "WHERE ki.qId =~ 'Q[0-9]+' " +
            "RETURN MAX(toInteger(substring(ki.qId, 1))) AS maxNumber")
    Long findMaxQIdNumber();
}