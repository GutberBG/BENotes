package com.example.BENotes.repository;

import com.example.BENotes.entity.Tag;
import com.example.BENotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByUserAndDeletedFalse(User user);
    Optional<Tag> findByName(String name);
    List<Tag> findByUserIdAndDeletedFalse(Long userId);
    Optional<Tag> findByNameAndUserId(String name, Long userId);
}