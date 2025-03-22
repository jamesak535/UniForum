package com.uniforum.service;

import com.uniforum.model.Post;
import com.uniforum.model.Topic;
import com.uniforum.model.User;
import com.uniforum.repository.PostRepository;
import com.uniforum.repository.TopicRepository;
import com.uniforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    
    public Page<Post> getPostsByTopic(Long topicId, Pageable pageable) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));
        
        return postRepository.findByTopicAndIsActiveTrue(topic, pageable);
    }
    
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByAuthor(user);
    }
    
    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        return postRepository.findByContentContaining(keyword, pageable);
    }
    
    @Transactional
    public Post createPost(Long topicId, Post post, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));
        
        post.setAuthor(user);
        post.setTopic(topic);
        
        return postRepository.save(post);
    }
    
    @Transactional
    public Post updatePost(Long id, Post postDetails, String username) {
        Post post = getPostById(id);
        
        // Check if the user is the author
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only edit your own posts");
        }
        
        post.setContent(postDetails.getContent());
        
        return postRepository.save(post);
    }
    
    @Transactional
    public void deletePost(Long id, String username) {
        Post post = getPostById(id);
        
        // Check if the user is the author
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only delete your own posts");
        }
        
        // Soft delete
        post.setActive(false);
        postRepository.save(post);
    }
}