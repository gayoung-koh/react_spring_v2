package com.example.react_spring_v2.domain.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {
  private static final long serialVersionUID = -947585423656694361L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String username;

  @Column
  private String password;

  @Builder
  public User(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return (
      "User [id=" + id + ", name=" + username + ", password=" + password + "]"
    );
  }
}
