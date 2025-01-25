package com.example.BENotes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString(exclude = {"notes", "tags"})
@EqualsAndHashCode(exclude = {"notes", "tags"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Note> notes;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Tag> tags;

}
