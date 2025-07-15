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

@Node("ReferenceProperty")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceInfo {
    @Id
    @Property("referenceInfoId")
    private String id;

    @Property("referenceInfoValue")
    private String value;

    @Property("referenceInfoValueType")
    private String valueType;

    @Property("order")
    private Integer order;

    @CreatedDate
    @Property("referenceInfoCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("referenceInfoUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "REFERENCE_INFO_USES_PROPERTY", direction = Relationship.Direction.OUTGOING)
    private Property property;

    public static class ValueType {
        public static final String ITEM = "item";
        public static final String STRING = "string";
        public static final String URL = "url";
        public static final String TIME = "time";
    }
}