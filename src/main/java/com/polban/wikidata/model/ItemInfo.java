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

@Node("ItemInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {
    @Id
    @Property("itemInfoId")
    private String id;

    @Property("itemInfoValue")
    private String value;

    @Property("itemInfoValueType")
    private String valueType;

    @CreatedDate
    @Property("itemInfoCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("itemInfoUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "ITEM_INFO_IN_LANGUAGE", direction = Relationship.Direction.OUTGOING)
    private Language language;

    public static class ValueType {
        public static final String LABEL = "label";
        public static final String DESCRIPTION = "description";
        public static final String ALIAS = "alias";
    }
}
