package com.example.react_spring_v2.domain.post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class Post implements Serializable {
  private static final long serialVersionUID = -947585423656694361L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column(length = 600)
  private String body;

  @Column
  private String tags;

  @Column
  private LocalDateTime publishedDate;

  @Builder
  public Post(Long id, String title, String body, String tags, LocalDateTime publishedDate) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.tags = tags;
    this.publishedDate = publishedDate;
  }
}
