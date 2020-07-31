package com.example.react_spring_v2.domain.post;

import com.example.react_spring_v2.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
