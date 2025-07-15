package com.polban.wikidata.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Language")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    @Id
    @Property("languageCode")
    private String code;

    @Property("languageName")
    private String name;

    @Property("languageVariant")
    private String variant;

    @CreatedDate
    @Property("languageCreatedAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("languageUpdatedAt")
    private LocalDateTime updatedAt;
}
