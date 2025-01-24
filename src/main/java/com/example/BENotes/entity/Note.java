package com.example.BENotes.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"notes", "tags"})
    private User user;

    @ManyToMany
    @JsonIgnoreProperties("notes")
    private Set<Tag> tags;

    // Getters and Setters
}