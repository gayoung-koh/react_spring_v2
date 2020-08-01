package com.example.react_spring_v2.service;

import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.repository.PostRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
  @Autowired
  private PostRepository postRepository;

  @Override
  public List<Post> getPosts(Pageable pageable) throws Exception {
    return postRepository.findAll(pageable).getContent();
  }

  @Override
  public Post getPost(Long Id) throws Exception {
    return postRepository.findById(Id).orElse(new Post());
  }

  @Override
  public Post postPost(Post post) throws Exception {
    return postRepository.save(post);
  }

  @Override
  public void deletePost(Long Id) throws Exception {
    postRepository.deleteById(Id);
  }

  @Override
  public Post findPostById(Long Id) throws Exception {
    return postRepository.findById(Id).orElse(new Post());
  }
}
