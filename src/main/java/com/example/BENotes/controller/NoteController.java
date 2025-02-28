package com.example.BENotes.controller;

import com.example.BENotes.dto.NoteDTO;
import com.example.BENotes.dto.SearchStateDTO;
import com.example.BENotes.entity.Note;
import com.example.BENotes.entity.SearchState;
import com.example.BENotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.createNote(noteDTO));
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getNotesByUser(@PathVariable Long userId) {

        return ResponseEntity.ok(noteService.getAllNotesByUser(userId));
    }

    @GetMapping("/user/{userId}/archived")
    public List<NoteDTO> getUserArchivedNotes(@PathVariable Long userId, @RequestParam boolean archived) {
        return noteService.getNotesByUserAndArchivedStatus(userId, archived);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoteDTO>> searchNotes(
            @RequestParam Long userId,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        // Guardar el estado de la búsqueda
        noteService.saveSearchState(userId, query, sortBy, order, false, false);
        List<NoteDTO> notes = noteService.searchNotes(userId, query, sortBy, order);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search/state")
    public ResponseEntity<List<SearchStateDTO>> getSearchState(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "5") int limit) {  // Default to 5
        List<SearchState> searchStates = noteService.getSearchStates(userId, limit);

        // Convertir SearchState a SearchStateDTO
        List<SearchStateDTO> response = searchStates.stream()
                .map(state -> new SearchStateDTO(
                        state.getId(),
                        state.getUser().getId(),  // Solo el ID del usuario
                        state.getQuery(),
                        state.getSortBy(),
                        state.getOrder(),
                        state.isArchived(),
                        state.isDeleted(),
                        state.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.updateNote(id, noteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<NoteDTO> archiveNote(@PathVariable Long id) {
        NoteDTO archivedNote = noteService.archiveNote(id);
        if (archivedNote != null) {
            return ResponseEntity.ok(archivedNote);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/unarchive")
    public ResponseEntity<NoteDTO> unarchiveNote(@PathVariable Long id) {
        NoteDTO unarchivedNote = noteService.unarchiveNote(id);
        if (unarchivedNote != null) {
            return ResponseEntity.ok(unarchivedNote);
        }
        return ResponseEntity.notFound().build();
    }
}
