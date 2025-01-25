package com.example.BENotes.dto;

import com.example.BENotes.entity.Tag;
import com.example.BENotes.entity.Note;
import java.util.stream.Collectors;

public class TagMapper {
    public static TagDTO toTagDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());

        // Mapear IDs de notas asociadas
        if (tag.getNotes() != null) {
            dto.setNotes(tag.getNotes().stream()
                    .map(note -> note.getId())
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
