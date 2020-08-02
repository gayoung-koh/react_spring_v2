package com.example.react_spring_v2.controller;

import com.example.react_spring_v2.config.security.JwtTokenProvider;
import com.example.react_spring_v2.config.security.UnauthorizedExeption;
import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.domain.user.User;
import com.example.react_spring_v2.repository.UserRepository;
import com.example.react_spring_v2.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
  @Autowired
  private PostService postService;

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

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
  public ResponseEntity<?> postPost(@RequestBody Post post, HttpServletRequest request)
      throws Exception {
      if(!checkLoggedIn(request)) {
        return new ResponseEntity<String>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
      }
      else {
        String username = getUserNameWithToken(request);
        if(username.equals("notValid")) {
          return new ResponseEntity<String>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }
        else {
          post.setPublishedDate(LocalDateTime.now());
          post.setUsername(username);
          Post returnedPost = postService.postPost(post);
          return ResponseEntity.ok(returnedPost);
        }
      }
    }
    /*else {
      return new ResponseEntity<String>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }*/
    //return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  //}

  /*
   *     수정
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> putPost(@PathVariable("id") Long id, @RequestBody Post editedPost, HttpServletRequest request)
      throws Exception {
    if(checkLoggedIn(request)) {
      Post post = postService.findPostById(id);
      if (post.getId() == null) {
        return new ResponseEntity<String>("포스트가 존재하지 않습니다", HttpStatus.NOT_FOUND);
      } else {
        if(post.getUsername().equals(getUserNameWithToken(request))) {
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
        else {
          return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
      }
    }
    else {
      return new ResponseEntity<String>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }
  }

  /*
   *     삭제
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable("id") Long id, HttpServletRequest request)
      throws Exception {
    if(checkLoggedIn(request)) {
      Post post = postService.findPostById(id);
      if (post.getId() == null) {
        return new ResponseEntity<String>("포스트가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
      }
      else {
        if(post.getUsername().equals(getUserNameWithToken(request))) {
          postService.deletePost(id);
          return new ResponseEntity<String>("SUCCESS", HttpStatus.NO_CONTENT);
        }
        else {
          return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
      }
    }
    else {
      return new ResponseEntity<String>("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }
  }

  public boolean checkLoggedIn(HttpServletRequest request) {
    boolean find = false;
    String value = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null) {
      for(Cookie cookie: cookies) {
        if("access_token".equals(cookie.getName())) {
          find = true;
          value = cookie.getValue();
          break;
        }
      }
    }
    else {
      return false;
    }
    if(find) {
      if(jwtTokenProvider.validateToken(value)) {
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }

  }

  public String getUserNameWithToken(HttpServletRequest request) {
    boolean find = false;
    String value = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null) {
      for(Cookie cookie: cookies) {
        if("access_token".equals(cookie.getName())) {
          find = true;
          value = cookie.getValue();
          break;
        }
      }
    }
    else {
      return "notValid";
    }
    if(find) {
      if(jwtTokenProvider.validateToken(value)) {
        return jwtTokenProvider.getUserPk(value);
      }
      else {
        return "notValid";
      }
    }
    else {
      return "notValid";
    }
  }
}
