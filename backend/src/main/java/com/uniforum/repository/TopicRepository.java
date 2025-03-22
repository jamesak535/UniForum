package com.uniforum.repository;

import com.uniforum.model.Topic;
import com.uniforum.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByAuthor(User author);
    List<Topic> findByCategory(String category);
    Page<Topic> findByIsActiveTrue(Pageable pageable);
    Page<Topic> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);
}