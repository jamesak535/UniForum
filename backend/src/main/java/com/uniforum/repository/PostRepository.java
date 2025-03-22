package com.uniforum.repository;

import com.uniforum.model.Post;
import com.uniforum.model.Topic;
import com.uniforum.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
    List<Post> findByTopic(Topic topic);
    Page<Post> findByTopicAndIsActiveTrue(Topic topic, Pageable pageable);
    Page<Post> findByContentContaining(String content, Pageable pageable);
}