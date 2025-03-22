package com.uniforum.controller;

import com.uniforum.dto.TopicRequest;
import com.uniforum.model.Topic;
import com.uniforum.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;
    
    @GetMapping
    public ResponseEntity<Page<Topic>> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
                
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        
        Page<Topic> topics = topicService.getActiveTopics(pageable);
        
        return ResponseEntity.ok(topics);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Topic>> getTopicsByCategory(@PathVariable String category) {
        List<Topic> topics = topicService.getTopicsByCategory(category);
        return ResponseEntity.ok(topics);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Topic>> searchTopics(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Topic> topics = topicService.searchTopics(keyword, pageable);
        
        return ResponseEntity.ok(topics);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Topic> createTopic(
            @Valid @RequestBody TopicRequest topicRequest,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setCategory(topicRequest.getCategory());
        
        Topic createdTopic = topicService.createTopic(topic, username);
        
        return ResponseEntity.ok(createdTopic);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Topic> updateTopic(
            @PathVariable Long id,
            @Valid @RequestBody TopicRequest topicRequest,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setCategory(topicRequest.getCategory());
        
        Topic updatedTopic = topicService.updateTopic(id, topic, username);
        
        return ResponseEntity.ok(updatedTopic);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Boolean>> deleteTopic(
            @PathVariable Long id,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        topicService.deleteTopic(id, username);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        
        return ResponseEntity.ok(response);
    }
}