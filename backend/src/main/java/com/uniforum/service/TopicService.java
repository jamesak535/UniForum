package com.uniforum.service;

import com.uniforum.model.Topic;
import com.uniforum.model.User;
import com.uniforum.repository.TopicRepository;
import com.uniforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    
    public Page<Topic> getActiveTopics(Pageable pageable) {
        return topicRepository.findByIsActiveTrue(pageable);
    }
    
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
    }

    public List<Topic> getTopicsByCategory(String category) {
        return topicRepository.findByCategory(category);
    }
    
    public List<Topic> getTopicsByUser(User user) {
        return topicRepository.findByAuthor(user);
    }
    
    public Page<Topic> searchTopics(String keyword, Pageable pageable) {
        return topicRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword, pageable);
    }
    
    @Transactional
    public Topic createTopic(Topic topic, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        
        topic.setAuthor(user);
        return topicRepository.save(topic);
    }
    
    @Transactional
    public Topic updateTopic(Long id, Topic topicDetails, String username) {
        Topic topic = getTopicById(id);
        
        // Check if the user is the author
        if (!topic.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only edit your own topics");
        }
        
        topic.setTitle(topicDetails.getTitle());
        topic.setDescription(topicDetails.getDescription());
        topic.setCategory(topicDetails.getCategory());
        
        return topicRepository.save(topic);
    }
    
    @Transactional
    public void deleteTopic(Long id, String username) {
        Topic topic = getTopicById(id);
        
        // Check if the user is the author
        if (!topic.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You can only delete your own topics");
        }
        
        // Soft delete
        topic.setActive(false);
        topicRepository.save(topic);
    }
}