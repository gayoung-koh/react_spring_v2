package com.example.react_spring_v2.controller;

import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  @Autowired
  private PostService postService;

  /*
   *     목록 조회
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getPosts() throws Exception {
    List<Post> posts = postService.getPosts(Sort.by(Direction.ASC, "id"));
    return ResponseEntity.ok(posts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPost(@PathVariable("id") Long id)
    throws Exception {
    Post post = postService.getPost(id);

    return ResponseEntity.ok(post);
  }

  /*
   *     등록
   */
  @PostMapping
  public ResponseEntity<String> postPost(@RequestBody Post post)
    throws Exception {
    postService.postPost(post);
    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }

  /*
   *     수정
   */
  @PutMapping("/{id}")
  public ResponseEntity<String> putPost(@PathVariable("id") Long id)
    throws Exception {
    Post post = postService.findPostById(id);

    postService.postPost(post);

    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }

  /*
   *     삭제
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable("id") Long id)
    throws Exception {
    postService.deletePost(id);

    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }
}
