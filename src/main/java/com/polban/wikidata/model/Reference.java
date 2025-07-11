package com.polban.wikidata.model;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Reference")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reference {
    @Id
    private String id;

    @Property("referenceId")
    private String referenceId;

    @Relationship(type = "HAS_REFERENCE_PROPERTY", direction = Relationship.Direction.OUTGOING)
    private Set<ReferenceProperty> referenceProperties;

    @CreatedDate
    @Property("createdAt")
    private LocalDateTime createdAt;
}