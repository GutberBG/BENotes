package com.example.BENotes.repository;

import com.example.BENotes.entity.SearchState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SearchStateRepository extends JpaRepository<SearchState, Long> {
    List<SearchState> findByUserId(Long userId);

    @Query("SELECT ss FROM SearchState ss WHERE ss.user.id = :userId ORDER BY ss.createdAt DESC")
    List<SearchState> findRecentSearchStates(Long userId, Pageable pageable);
}