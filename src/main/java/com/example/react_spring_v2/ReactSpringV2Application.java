package com.example.react_spring_v2;

import com.example.react_spring_v2.domain.user.User;
import com.example.react_spring_v2.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactSpringV2Application {

  public static void main(String[] args) {
    SpringApplication.run(ReactSpringV2Application.class, args);
  }

  @Bean
  public CommandLineRunner runner(UserRepository userRepository)
    throws Exception {
    return args -> {
      IntStream
        .rangeClosed(1, 10)
        .forEach(
          index ->
            userRepository.save(
              User
                .builder()
                .username("username" + index)
                .password("password" + index)
                .build()
            )
        );
    };
  }
}
