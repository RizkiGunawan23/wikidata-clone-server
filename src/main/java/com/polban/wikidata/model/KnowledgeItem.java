package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("KnowledgeItem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeItem {
    @Id
    @Property("qId")
    private String qId;

    @Property("labels")
    private String labels;

    @Property("descriptions")
    private String descriptions;

    @Property("aliases")
    private String aliases;

    // @Property("status")
    // private String status;

    @Relationship(type = "HAS_STATEMENT", direction = Relationship.Direction.OUTGOING)
    private Set<Statement> statements;

    @Relationship(type = "HAS_SITELINK", direction = Relationship.Direction.OUTGOING)
    private Set<Sitelink> sitelinks;

    @CreatedDate
    @Property("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private User createdBy;

    @Relationship(type = "LAST_MODIFIED_BY", direction = Relationship.Direction.OUTGOING)
    private User lastModifiedBy;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> getDescriptionsMap() {
        try {
            return descriptions == null ? null
                    : mapper.readValue(descriptions, new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception e) {
            return null;
        }
    }

    public void setDescriptionsMap(Map<String, String> descriptionsMap) {
        try {
            this.descriptions = descriptionsMap == null ? null : mapper.writeValueAsString(descriptionsMap);
        } catch (Exception e) {
            this.descriptions = null;
        }
    }

    public Map<String, List<String>> getAliasesMap() {
        try {
            return aliases == null ? null : mapper.readValue(aliases, new TypeReference<Map<String, List<String>>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }

    public void setAliasesMap(Map<String, List<String>> aliasesMap) {
        try {
            this.aliases = aliasesMap == null ? null : mapper.writeValueAsString(aliasesMap);
        } catch (Exception e) {
            this.aliases = null;
        }
    }

    // public static class Status {
    // public static final String ACTIVE = "active";
    // public static final String INACTIVE = "inactive";
    // public static final String DELETED = "deleted";
    // }
}
