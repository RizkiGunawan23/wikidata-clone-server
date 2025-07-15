package com.polban.wikidata.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("PropertyInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyInfo {
    @Id
    @Property("propertyInfoId")
    private String id;

    @Property("propertyInfoValue")
    private String value;

    @Property("propertyInfoValueType")
    private String valueType;

    @CreatedDate
    @Property("propertyInfoCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("propertyInfoUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "PROPERTY_INFO_IN_LANGUAGE", direction = Relationship.Direction.OUTGOING)
    private Language language;

    public static class ValueType {
        public static final String LABEL = "label";
        public static final String DESCRIPTION = "description";
        public static final String ALIAS = "alias";
    }
}
