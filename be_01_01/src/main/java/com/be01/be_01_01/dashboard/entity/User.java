package com.be01.be_01_01.dashboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNum;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
}
