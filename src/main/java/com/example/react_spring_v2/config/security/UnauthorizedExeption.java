package com.example.react_spring_v2.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedExeption extends RuntimeException{
  public UnauthorizedExeption(String message) {
    super(message);
  }
}
