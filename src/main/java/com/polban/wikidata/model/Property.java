package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @org.springframework.data.neo4j.core.schema.Property("labels")
    private String labels;

    @org.springframework.data.neo4j.core.schema.Property("descriptions")
    private String descriptions;

    @org.springframework.data.neo4j.core.schema.Property("aliases")
    private String aliases;

    @org.springframework.data.neo4j.core.schema.Property("datatype")
    private String datatype;

    @org.springframework.data.neo4j.core.schema.Property("status")
    private String status;

    @CreatedDate
    @org.springframework.data.neo4j.core.schema.Property("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @org.springframework.data.neo4j.core.schema.Property("updatedAt")
    private LocalDateTime updatedAt;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> getLabels() {
        try {
            return labels == null ? null : mapper.readValue(labels, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }

    public void setLabels(Map<String, String> labels) {
        try {
            this.labels = labels == null ? null : mapper.writeValueAsString(labels);
        } catch (Exception e) {
            this.labels = null;
        }
    }

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
