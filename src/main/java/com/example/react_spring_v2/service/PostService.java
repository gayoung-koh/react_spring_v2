package com.example.react_spring_v2.service;

import com.example.react_spring_v2.domain.post.Post;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface PostService {
  List<Post> getPosts(Sort sort) throws Exception;

  Post getPost(Long Id) throws Exception;

  void postPost(Post post) throws Exception;

  void deletePost(Long Id) throws Exception;

  Post findPostById(Long Id) throws Exception;
}
