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

@Node("Reference")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reference {
    @Id
    @Property("referenceId")
    private String id;

    @CreatedDate
    @Property("referenceCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("referenceUpdatedAt")
    private LocalDateTime updatedAt;

    @Relationship(type = "HAS_REFERENCE_INFO", direction = Relationship.Direction.OUTGOING)
    private Set<ReferenceInfo> referenceInfos;
}