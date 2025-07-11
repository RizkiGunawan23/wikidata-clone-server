package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String id;

    @Property("site")
    private String site;

    @Property("title")
    private String title;

    @Property("url")
    private String url;

    @Property("badges")
    private String badges;

    @CreatedDate
    @Property("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updatedAt")
    private LocalDateTime updatedAt;

    private static final ObjectMapper mapper = new ObjectMapper();

    public List<String> getBadges() {
        try {
            return badges == null ? null : mapper.readValue(badges, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }

    public void setBadges(List<String> badges) {
        try {
            this.badges = badges == null ? null : mapper.writeValueAsString(badges);
        } catch (Exception e) {
            this.badges = null;
        }
    }

    public static class Badge {
        public static final String FEATURED = "featured";
        public static final String GOOD = "good";
    }
}