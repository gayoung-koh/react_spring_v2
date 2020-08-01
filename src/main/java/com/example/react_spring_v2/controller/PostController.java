package com.example.react_spring_v2.controller;

import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  @Autowired
  private PostService postService;

  /*
   *     목록 조회
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getPosts(Pageable pageable) throws Exception {
    List<Post> posts = postService.getPosts(pageable);
    return ResponseEntity.ok(posts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPost(@PathVariable("id") Long id)
      throws Exception {
    Post post = postService.getPost(id);
    if (post.getId() == null) {
      return new ResponseEntity<String>("포스트가 존재하지 않습니다", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(post);
  }

  /*
   *     등록
   */
  @PostMapping
  public ResponseEntity<Post> postPost(@RequestBody Post post)
      throws Exception {
    post.setPublishedDate(LocalDateTime.now());
    Post returnedPost = postService.postPost(post);
    return ResponseEntity.ok(returnedPost);
    //return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }

  /*
   *     수정
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> putPost(@PathVariable("id") Long id, @RequestBody Post editedPost)
      throws Exception {
    Post post = postService.findPostById(id);
    if (post.getId() == null) {
      return new ResponseEntity<String>("포스트가 존재하지 않습니다", HttpStatus.NOT_FOUND);
    } else {
      if (editedPost.getTitle() != null)
        post.setTitle(editedPost.getTitle());
      if (editedPost.getBody() != null)
        post.setBody(editedPost.getBody());
      if (editedPost.getTags() != null)
        post.setTags((editedPost.getTags()));
      post.setPublishedDate(LocalDateTime.now());
      //Post returnedPost = postService.postPost(editedPost);
      return ResponseEntity.ok(post);
      //return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
  }

  /*
   *     삭제
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable("id") Long id)
      throws Exception {
    Post post = postService.findPostById(id);
    if (post.getId() == null) {
      return new ResponseEntity<String>("포스트가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
    }
    postService.deletePost(id);
    return new ResponseEntity<String>("SUCCESS", HttpStatus.NO_CONTENT);
  }
}
