package com.example.BENotes.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"user", "tags"})
@EqualsAndHashCode(exclude = {"user", "tags"})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private boolean archived;

    private Date createdAt;
    private Date updatedAt;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"notes", "tags"})
    private User user;

    @ManyToMany
    @JsonIgnoreProperties("notes")
    private Set<Tag> tags;


    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}