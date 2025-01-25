package com.example.BENotes.service;

import com.example.BENotes.dto.TagDTO;
import com.example.BENotes.dto.TagMapper;
import com.example.BENotes.entity.Tag;
import com.example.BENotes.entity.User;
import com.example.BENotes.repository.TagRepository;
import com.example.BENotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    public Tag createTagForUser(Long userId, String tagName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setUser(user);  // Asociar el tag al usuario

        return tagRepository.save(tag);
    }

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagMapper::toTagDTO)
                .collect(Collectors.toList());
    }

    public List<TagDTO> getTagsByUser(Long userId) {
        // Verifica que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("El usuario con ID " + userId + " no existe.");
        }

        // Obtén todas las etiquetas cuyo campo 'deleted' sea false y procesa las notas asociadas
        return tagRepository.findAll().stream()
                .filter(tag -> !tag.isDeleted()) // Filtrar etiquetas no eliminadas
                .map(tag -> {
                    TagDTO tagDTO = TagMapper.toTagDTO(tag);
                    // Filtra las notas por usuario y añade solo los IDs de las que correspondan
                    tagDTO.setNotes(tag.getNotes().stream()
                            .filter(note -> note.getUser().getId().equals(userId))
                            .map(note -> note.getId())
                            .collect(Collectors.toList()));
                    return tagDTO;
                })
                .collect(Collectors.toList());
    }

    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setDeleted(true); // Marcar la etiqueta como eliminada
        tagRepository.save(tag);
    }
}