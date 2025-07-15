package com.polban.wikidata.model;

import java.time.LocalDateTime;
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

@Node("Qualifier")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qualifier {
    @Id
    @Property("qualifierId")
    private String id;

    @Property("qualifierValue")
    private String value;

    @Property("qualifierValueType")
    private String valueType;

    @CreatedDate
    @Property("qualifierCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("qualifierUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "QUALIFIER_USES_PROPERTY", direction = Relationship.Direction.OUTGOING)
    private Property property;

    public static class ValueType {
        public static final String ITEM = "item";
        public static final String STRING = "string";
        public static final String TIME = "time";
        public static final String QUANTITY = "quantity";
    }
}