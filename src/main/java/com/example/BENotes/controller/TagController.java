package com.example.BENotes.controller;

import com.example.BENotes.entity.Tag;
import com.example.BENotes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/user/{userId}")
    public Tag createTagForUser(@PathVariable Long userId, @RequestBody Tag tag) {
        return tagService.createTagForUser(userId, tag.getName());
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/user/{userId}")
    public List<Tag> getTagsByUser(@PathVariable Long userId) {
        return tagService.getTagsByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}