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

@Node("Item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @Property("qId")
    private String qId;

    // @Property("status")
    // private String status;

    @CreatedDate
    @Property("itemCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("itemUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "HAS_ITEM_INFO", direction = Relationship.Direction.OUTGOING)
    private Set<ItemInfo> itemInfos;

    @Relationship(type = "HAS_STATEMENT", direction = Relationship.Direction.OUTGOING)
    private Set<Statement> statements;

    @Relationship(type = "HAS_SITELINK", direction = Relationship.Direction.OUTGOING)
    private Set<Sitelink> sitelinks;

    @Relationship(type = "ITEM_CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private User createdBy;

    @Relationship(type = "ITEM_MODIFIED_BY", direction = Relationship.Direction.OUTGOING)
    private User modifiedBy;

    // public static class Status {
    // public static final String ACTIVE = "active";
    // public static final String INACTIVE = "inactive";
    // public static final String DELETED = "deleted";
    // }
}