package com.example.BENotes.service;


import com.example.BENotes.dto.NoteDTO;
import com.example.BENotes.dto.NoteMapper;
import com.example.BENotes.entity.Note;
import com.example.BENotes.entity.SearchState;
import com.example.BENotes.entity.Tag;
import com.example.BENotes.entity.User;
import com.example.BENotes.repository.NoteRepository;
import com.example.BENotes.repository.SearchStateRepository;
import com.example.BENotes.repository.TagRepository;
import com.example.BENotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SearchStateRepository searchStateRepository;

    public NoteDTO createNote(NoteDTO noteDTO) {
        // Crear la nota
        Note note = new Note();

        // Buscar al usuario por ID
        User user = userRepository.findById(noteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        note.setUser(user);

        // Procesar las etiquetas
        Set<Tag> tags = noteDTO.getTags().stream()
                .map(tagName -> {
                    // Buscar si la etiqueta ya existe
                    Tag existingTag = tagRepository.findByName(tagName).orElse(null);

                    // Si la etiqueta existe pero no pertenece al usuario, crear una nueva
                    if (existingTag != null && !existingTag.getUser().getId().equals(user.getId())) {
                        // Crear una nueva etiqueta asociada al usuario actual
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        newTag.setUser(user);
                        return tagRepository.save(newTag);
                    }

                    // Si la etiqueta no existe, crear una nueva y asociarla al usuario
                    if (existingTag == null) {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        newTag.setUser(user);
                        return tagRepository.save(newTag);
                    }

                    // Si la etiqueta existe y pertenece al usuario, devolverla
                    return existingTag;
                })
                .collect(Collectors.toSet());

        // Asignar las etiquetas a la nota
        note.setTags(tags);

        // Asignar otros atributos de la nota
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setArchived(noteDTO.isArchived());
        note.setDeleted(noteDTO.isDeleted());

        // Guardar la nota en el repositorio
        Note savedNote = noteRepository.save(note);

        // Convertir la nota guardada a DTO y devolverla
        return convertToDTO(savedNote);
    }

    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(NoteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }
    public List<NoteDTO> getAllNotesByUser(Long userId) {
        return noteRepository.findByUserIdAndDeletedFalse(userId).stream()
                .map(NoteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getNotesByUserAndArchivedStatus(Long userId, boolean archived) {
        return noteRepository.findByUserIdAndArchivedAndDeletedFalse(userId, archived).stream()
                .map(NoteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    public NoteDTO getNoteById(Long id) {
        return noteRepository.findById(id)
                .map(NoteMapper::toNoteDTO)
                .orElse(null);
    }

    public List<NoteDTO> searchNotes(Long userId, String query, String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

        List<Note> notes;

        if (query != null && !query.isBlank()) {
            // Buscar por título, contenido o etiquetas
            notes = noteRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrTagsNameContainingIgnoreCaseAndUserIdAndDeletedFalse(
                    query, query, query, userId
            );
        } else {
            // Obtener todas las notas del usuario con ordenamiento
            notes = noteRepository.findByUserIdAndDeletedFalse(userId, sort);
        }

        return notes.stream()
                .map(NoteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    public void saveSearchState(Long userId, String query, String sortBy, String order, boolean archived, boolean deleted) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Crear un nuevo estado de búsqueda
        SearchState searchState = new SearchState();
        searchState.setUser(user);
        searchState.setQuery(query);
        searchState.setSortBy(sortBy);
        searchState.setOrder(order);
        searchState.setArchived(archived);
        searchState.setDeleted(deleted);

        searchStateRepository.save(searchState);
    }

    public List<SearchState> getSearchStates(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return searchStateRepository.findRecentSearchStates(userId, pageable);
    }

    public NoteDTO updateNote(Long id, NoteDTO noteDTO) {
        // Buscar la nota existente
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar y asignar usuario
        User user = userRepository.findById(noteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setContent(noteDTO.getContent());
        existingNote.setArchived(noteDTO.isArchived());
        existingNote.setDeleted(noteDTO.isDeleted());
        existingNote.setUser(user);

        // Gestionar etiquetas
        Set<Tag> updatedTags = noteDTO.getTags().stream()
                .map(tagName -> {
                    Tag existingTag = tagRepository.findByName(tagName).orElse(null);
                    if (existingTag != null) {
                        // Validar que la etiqueta pertenece al usuario
                        if (!existingTag.getUser().getId().equals(user.getId())) {
                            throw new RuntimeException("Tag belongs to a different user");
                        }
                        return existingTag;
                    }
                    // Crear una nueva etiqueta si no existe
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    newTag.setUser(user);
                    return tagRepository.save(newTag);
                })
                .collect(Collectors.toSet());

        // Eliminar relaciones de etiquetas no incluidas
        existingNote.getTags().removeIf(tag -> !updatedTags.contains(tag));

        existingNote.setTags(updatedTags);

        Note updatedNote = noteRepository.save(existingNote);

        return convertToDTO(updatedNote);
    }

    public void deleteNote(Long id) {
        noteRepository.findById(id).ifPresent(note -> {
            note.setDeleted(true);
            noteRepository.save(note);
        });
    }

    public NoteDTO archiveNote(Long id) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setArchived(true);
                    return NoteMapper.toNoteDTO(noteRepository.save(note));
                })
                .orElse(null);
    }

    public NoteDTO unarchiveNote(Long id) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setArchived(false);
                    return NoteMapper.toNoteDTO(noteRepository.save(note));
                })
                .orElse(null);
    }

    private NoteDTO convertToDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setContent(note.getContent());
        noteDTO.setArchived(note.isArchived());
        noteDTO.setCreatedAt(note.getCreatedAt());
        noteDTO.setUpdatedAt(note.getUpdatedAt());
        noteDTO.setDeleted(note.isDeleted());
        noteDTO.setUserId(note.getUser().getId());
        noteDTO.setTags(note.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet()));
        return noteDTO;
    }
}