package com.example.BENotes.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private Long id;
    private String name;
    private List<Long> notes;
}
