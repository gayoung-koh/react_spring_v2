package com.example.react_spring_v2.domain.user;

import com.example.react_spring_v2.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
