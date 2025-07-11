package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Statement")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statement {
    @Id
    private String id;

    @Property("rank")
    private String rank;

    @Property("value")
    private String value;

    @Property("valueType")
    private String valueType;

    @Relationship(type = "USES_PROPERTY", direction = Relationship.Direction.OUTGOING)
    private Property property;

    @Relationship(type = "HAS_QUALIFIER", direction = Relationship.Direction.OUTGOING)
    private Set<Qualifier> qualifiers;

    @Relationship(type = "HAS_REFERENCE", direction = Relationship.Direction.OUTGOING)
    private Set<Reference> references;

    @CreatedDate
    @Property("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private User createdBy;

    public static class Rank {
        public static final String PREFERRED = "preferred";
        public static final String NORMAL = "normal";
        public static final String DEPRECATED = "deprecated";
    }

    public static class ValueType {
        public static final String ITEM = "item";
        public static final String STRING = "string";
        public static final String TIME = "time";
        public static final String QUANTITY = "quantity";
    }
}