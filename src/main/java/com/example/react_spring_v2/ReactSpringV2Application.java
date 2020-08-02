package com.example.react_spring_v2;

import com.example.react_spring_v2.domain.post.Post;
import com.example.react_spring_v2.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class ReactSpringV2Application {

  public static void main(String[] args) {
    SpringApplication.run(ReactSpringV2Application.class, args);
  }

 /* @Bean
  public CommandLineRunner runner(PostRepository postRepository)
      throws Exception {
    return args -> {
      IntStream
          .rangeClosed(1, 40)
          .forEach(
              index ->
                  postRepository.save(
                      Post.builder().title("포스트" + index).body("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").tags("가짜 데이터").publishedDate(LocalDateTime.now()).build()
                  )
          );
    };
  }*/
}
