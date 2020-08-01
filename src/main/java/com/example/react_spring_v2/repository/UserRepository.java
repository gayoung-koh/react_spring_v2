package com.example.react_spring_v2.repository;

import com.example.react_spring_v2.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByName(String name);
}
