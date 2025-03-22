package com.uniforum.controller;

import com.uniforum.dto.PostRequest;
import com.uniforum.model.Post;
import com.uniforum.service.PostService;
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
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<Page<Post>> getPostsByTopic(
            @PathVariable Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
                
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        
        Page<Post> posts = postService.getPostsByTopic(topicId, pageable);
        
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Post>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.searchPosts(keyword, pageable);
        
        return ResponseEntity.ok(posts);
    }
    
    @PostMapping("/topic/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> createPost(
            @PathVariable Long topicId,
            @Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        Post post = new Post();
        post.setContent(postRequest.getContent());
        
        Post createdPost = postService.createPost(topicId, post, username);
        
        return ResponseEntity.ok(createdPost);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        Post post = new Post();
        post.setContent(postRequest.getContent());
        
        Post updatedPost = postService.updatePost(id, post, username);
        
        return ResponseEntity.ok(updatedPost);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Boolean>> deletePost(
            @PathVariable Long id,
            Authentication authentication) {
        
        String username = authentication.getName();
        
        postService.deletePost(id, username);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        
        return ResponseEntity.ok(response);
    }
}