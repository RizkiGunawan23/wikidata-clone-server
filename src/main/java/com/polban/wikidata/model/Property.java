package com.polban.wikidata.model;

import java.util.Map;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;

@Node("Property")
@Data
public class Property {
    @Id
    private String pid;

    private Map<String, String> labels;
    private Map<String, String> descriptions;
    private String datatype;
}
