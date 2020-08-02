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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  public ResponseEntity<?> register(@RequestBody User user, HttpServletResponse response) {
    Optional<User> foundUser = userRepository.findByName(user.getName());
    if(foundUser.isPresent()) {
      return new ResponseEntity<String>("Conflict",HttpStatus.CONFLICT);
    }
    User returnedUser = userRepository.save(User.builder().name(user.getName()).password(passwordEncoder.encode(user.getPassword())).roles(Collections.singletonList("ROLE_USER")).build());
    String token = jwtTokenProvider.createToken(returnedUser.getUsername(), returnedUser.getRoles());
    Cookie cookie = new Cookie("access_token", token);
    cookie.setMaxAge(60 * 60 * 24 * 365);
    cookie.setPath("/");
    response.addCookie(cookie);
    return ResponseEntity.ok(returnedUser);
  }

  // 로그인
  @PostMapping("/api/auth/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> user, HttpServletResponse response) {
    User member = userRepository.findByName(user.get("name"))
        .orElseThrow(() ->
            new UnauthorizedExeption("가입되지 않은 id 입니다.")
                //IllegalArgumentException("가입되지 않은 id 입니다.")
        );
    if(!passwordEncoder.matches(user.get("password"), member.getPassword())) {
      throw new UnauthorizedExeption("잘못된 비밀번호입니다.");
    }
    String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    Cookie cookie = new Cookie("access_token", token);
    cookie.setMaxAge(60 * 60 * 24 * 365);
    cookie.setPath("/");
    response.addCookie(cookie);
    return ResponseEntity.ok(member);
  }

  // check
  @GetMapping("/api/auth/check")
  public ResponseEntity<?> check(HttpServletRequest request) {
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
      return new ResponseEntity<String>("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
    if(find) {
      if(jwtTokenProvider.validateToken(value)) {
        String userpk = jwtTokenProvider.getUserPk(value);
        Optional<User> foundUser = userRepository.findByName(userpk);
        return ResponseEntity.ok(foundUser);
      }
      else {
        return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
      }
    }
    else {
      return new ResponseEntity<String>("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/api/auth/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    Cookie tokenCookie = new Cookie("access_token", null);
    tokenCookie.setMaxAge(0);
    tokenCookie.setPath("/");
    response.addCookie(tokenCookie);

    return new ResponseEntity<String>("로그아웃 되었습니다.", HttpStatus.NO_CONTENT);
  }
}