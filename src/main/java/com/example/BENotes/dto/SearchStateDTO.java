package com.example.BENotes.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchStateDTO {
    private Long id;
    private Long userId;
    private String query;
    private String sortBy;
    private String order;
    private boolean archived;
    private boolean deleted;
    private LocalDateTime createdAt;

    public SearchStateDTO(Long id, Long userId, String query, String sortBy, String order, boolean archived, boolean deleted, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.query = query;
        this.sortBy = sortBy;
        this.order = order;
        this.archived = archived;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }
}
