package com.polban.wikidata.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Property("username")
    private String username;

    @Property("email")
    private String email;

    @Property("password")
    private String password;

    @Property("role")
    private String role;

    @CreatedDate
    @Property("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updatedAt")
    private LocalDateTime updatedAt;

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
}
