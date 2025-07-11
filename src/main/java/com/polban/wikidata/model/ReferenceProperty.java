package com.polban.wikidata.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("ReferenceProperty")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceProperty {
    @Id
    private String id;

    @Property("value")
    private String value;

    @Property("valueType")
    private String valueType;

    @Relationship(type = "USES_PROPERTY", direction = Relationship.Direction.OUTGOING)
    private Property property;

    @Property("order")
    private Integer order;

    public static class ValueType {
        public static final String ITEM = "item";
        public static final String STRING = "string";
        public static final String URL = "url";
        public static final String TIME = "time";
    }
}