package com.example.BENotes.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
public class NoteDTO {

    private Long id;
    private String title;
    private String content;
    private boolean archived;
    private Date createdAt;
    private Date updatedAt;
    private boolean deleted;
    private Long userId;
    private Set<String> tags;


}