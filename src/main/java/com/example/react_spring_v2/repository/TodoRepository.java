package com.example.react_spring_v2.repository;

import com.example.react_spring_v2.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
