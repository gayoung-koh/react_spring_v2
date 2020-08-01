package com.example.react_spring_v2.controller;

import com.example.react_spring_v2.config.security.JwtTokenProvider;
import com.example.react_spring_v2.config.security.UnauthorizedExeption;
import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.domain.user.User;
import com.example.react_spring_v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.CollectionUpdateAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  // 회원가입
  @PostMapping("/api/auth/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    Optional<User> foundUser = userRepository.findByName(user.getName());
    if(foundUser.isPresent()) {
      return new ResponseEntity<String>("Conflict",HttpStatus.CONFLICT);
    }
    User returnedUser = userRepository.save(User.builder().name(user.getName()).password(passwordEncoder.encode(user.getPassword())).roles(Collections.singletonList("ROLE_USER")).build());
    return ResponseEntity.ok(returnedUser);
  }

  // 로그인
  @PostMapping("/api/auth/login")
  public String login(@RequestBody Map<String, String> user) {
    User member = userRepository.findByName(user.get("name"))
        .orElseThrow(() ->
            new UnauthorizedExeption("가입되지 않은 id 입니다.")
                //IllegalArgumentException("가입되지 않은 id 입니다.")
        );
    if(!passwordEncoder.matches(user.get("password"), member.getPassword())) {
      throw new UnauthorizedExeption("잘못된 비밀번호입니다.");
    }
    return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
  }
}