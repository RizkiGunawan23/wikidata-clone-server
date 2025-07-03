package com.polban.wikidata.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("WikidataItem")
public class WikidataItem {
    @Id
    private String qid;

    @Property
    private Map<String, String> labels;

    @Property
    private Map<String, String> descriptions;

    @Property
    private List<String> aliases;

    // private List<Statement> statements;
}
