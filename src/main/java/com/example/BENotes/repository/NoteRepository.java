package com.example.BENotes.repository;

import com.example.BENotes.entity.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserIdAndDeletedFalse(Long userId);
    List<Note> findByUserIdAndDeletedFalse(Long userId, Sort sort);
    List<Note> findByArchivedAndDeletedFalse(boolean archived);

    List<Note> findByUserIdAndArchivedAndDeletedFalse(Long userId, boolean archived);
    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrTagsNameContainingIgnoreCaseAndUserIdAndDeletedFalse(
            String title, String content, String tag, Long userId);
}