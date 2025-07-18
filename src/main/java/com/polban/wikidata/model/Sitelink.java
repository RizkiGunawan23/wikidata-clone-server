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

@Node("Sitelink")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sitelink {
    @Id
    @Property("sitelinkId")
    private String id;

    @Property("site")
    private String site;

    @Property("title")
    private String title;

    @Property("url")
    private String url;

    @Property("badge")
    private String badge;

    @CreatedDate
    @Property("sitelinkCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("sitelinkUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "SITELINK_IN_LANGUAGE", direction = Relationship.Direction.OUTGOING)
    private Language language;

    public static class Badge {
        public static final String FEATURED = "featured";
        public static final String GOOD = "good";
    }
}