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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Integer phoneNum;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
