package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Property")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @Id
    @org.springframework.data.neo4j.core.schema.Property("pId")
    private String pId;

    @org.springframework.data.neo4j.core.schema.Property("dataType")
    private String dataType;

    @org.springframework.data.neo4j.core.schema.Property("propertyStatus")
    private String status;

    @CreatedDate
    @org.springframework.data.neo4j.core.schema.Property("propertyCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @org.springframework.data.neo4j.core.schema.Property("propertyUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "HAS_PROPERTY_INFO", direction = Relationship.Direction.OUTGOING)
    private Set<PropertyInfo> propertyInfos;

    public static class Datatype {
        public static final String WIKIBASE_ITEM = "wikibase-item";
        public static final String STRING = "string";
        public static final String TIME = "time";
        public static final String QUANTITY = "quantity";
        public static final String GEO_SHAPE = "geo-shape";
        public static final String URL = "url";
        public static final String MONOLINGUAL_TEXT = "monolingualtext";
    }

    public static class Status {
        public static final String ACTIVE = "active";
        public static final String DEPRECATED = "deprecated";
    }
}
