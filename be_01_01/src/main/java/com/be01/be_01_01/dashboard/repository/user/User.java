package com.be01.be_01_01.dashboard.repository.user;

import com.be01.be_01_01.dashboard.repository.board.Board;
import com.be01.be_01_01.dashboard.repository.comment.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name",nullable = false,length = 45) // VARCHAR 글자 제한 추가
    private String name;

    @Column(name = "email",nullable = false, unique = true,length = 45) // VARCHAR 글자 제한 추가
    private String email;

    @Column(name = "phone_num",nullable = false, unique = true)
    private String phoneNum;  // Iteger - > String sql도 VARCHAR로 변경

    @Column(name = "password",nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
