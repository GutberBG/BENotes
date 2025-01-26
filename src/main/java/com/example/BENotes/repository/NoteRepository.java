package com.example.BENotes.repository;

import com.example.BENotes.entity.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserIdAndDeletedFalse(Long userId);
    List<Note> findByUserIdAndDeletedFalse(Long userId, Sort sort);
    List<Note> findByArchivedAndDeletedFalse(boolean archived);

    List<Note> findByUserIdAndArchivedAndDeletedFalse(Long userId, boolean archived);
    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrTagsNameContainingIgnoreCaseAndUserIdAndDeletedFalse(
            String title, String content, String tag, Long userId);

    @Query("SELECT n FROM Note n " +
            "LEFT JOIN n.tags t " + // Unir las etiquetas
            "WHERE (n.title LIKE %:query% OR n.content LIKE %:query% OR t.name LIKE %:query%) " +
            "AND n.user.id = :userId " +
            "AND n.deleted = false")
    List<Note> searchNotes(@Param("query") String query, @Param("userId") Long userId, Sort sort);
}