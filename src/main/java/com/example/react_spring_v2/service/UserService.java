package com.example.react_spring_v2.service;

import com.example.react_spring_v2.domain.user.User;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface UserService {
  List<User> getUsers(Sort sort) throws Exception;

  User getUser(Long Id) throws Exception;

  void postUser(User user) throws Exception;

  void deleteUser(Long Id) throws Exception;

  User findUserById(Long Id) throws Exception;
}
