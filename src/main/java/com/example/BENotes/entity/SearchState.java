package com.example.BENotes.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class SearchState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String query; // Término de búsqueda

    private String sortBy; // Campo de ordenamiento

    private String order; // Dirección de ordenamiento (asc, desc)

    private boolean archived; // Filtro de notas archivadas

    private boolean deleted;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
