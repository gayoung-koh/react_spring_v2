package com.example.react_spring_v2.domain;

import com.example.react_spring_v2.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
