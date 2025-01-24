package com.example.BENotes.service;

import com.example.BENotes.entity.Tag;
import com.example.BENotes.entity.User;
import com.example.BENotes.repository.TagRepository;
import com.example.BENotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}