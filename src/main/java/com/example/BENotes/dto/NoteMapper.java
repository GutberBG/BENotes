package com.example.BENotes.dto;

import com.example.BENotes.entity.Note;

import java.util.stream.Collectors;

public class NoteMapper {

    public static NoteDTO toNoteDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setArchived(note.isArchived());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        dto.setDeleted(note.isDeleted());

        // Mapear userId
        if (note.getUser() != null) {
            dto.setUserId(note.getUser().getId());
        }

        // Mapear tags
        if (note.getTags() != null) {
            dto.setTags(note.getTags().stream()
                    .map(tag -> tag.getName())
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public static Note toEntity(NoteDTO dto) {
        Note note = new Note();
        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setArchived(dto.isArchived());
        note.setCreatedAt(dto.getCreatedAt());
        note.setUpdatedAt(dto.getUpdatedAt());
        note.setDeleted(dto.isDeleted());
        return note;
    }
}
