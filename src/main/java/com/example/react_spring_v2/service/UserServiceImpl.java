package com.example.react_spring_v2.service;

import com.example.react_spring_v2.domain.user.User;
import com.example.react_spring_v2.domain.user.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getUsers(Sort sort) throws Exception {
    return userRepository.findAll(sort);
  }

  @Override
  public User getUser(Long Id) throws Exception {
    return userRepository.findById(Id).orElse(new User());
  }

  @Override
  public void postUser(User user) throws Exception {
    userRepository.save(user);
  }

  @Override
  public void deleteUser(Long Id) throws Exception {
    userRepository.deleteById(Id);
  }

  @Override
  public User findUserById(Long Id) throws Exception {
    return userRepository.findById(Id).orElse(new User());
  }
}
