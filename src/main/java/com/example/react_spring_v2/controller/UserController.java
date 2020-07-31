package com.example.react_spring_v2.controller;

import com.example.react_spring_v2.domain.user.User;
import com.example.react_spring_v2.service.UserService;
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
@RequestMapping("/api/auth/register")
public class UserController {
  @Autowired
  private UserService userService;

  /*
   *     목록 조회
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUsers() throws Exception {
    List<User> users = userService.getUsers(Sort.by(Direction.ASC, "id"));
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable("id") Long id)
    throws Exception {
    User user = userService.getUser(id);

    return ResponseEntity.ok(user);
  }

  /*
   *     등록
   */
  @PostMapping
  public ResponseEntity<String> postUser(@RequestBody User user)
    throws Exception {
    userService.postUser(user);
    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }

  /*
   *     수정
   */
  @PutMapping("/{id}")
  public ResponseEntity<String> putUser(@PathVariable("id") Long id)
    throws Exception {
    User user = userService.findUserById(id);

    userService.postUser(user);

    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }

  /*
   *     삭제
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id)
    throws Exception {
    userService.deleteUser(id);

    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
  }
}
