package com.be01.be_01_01.dashboard.repository.User;

import com.be01.be_01_01.dashboard.repository.Comment.Comment;
import com.be01.be_01_01.dashboard.repository.Post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "author",nullable = false,length = 45) // VARCHAR 글자 제한 추가
    private String author;

    @Column(name = "email",nullable = false, unique = true,length = 45) // VARCHAR 글자 제한 추가
    private String email;

    @Column(name = "phone_num",nullable = false, unique = true)
    private String phoneNum;  // Integer - > String sql도 VARCHAR로 변경

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> boards = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    // 사용자의 역할을 Spring Security의 GrantedAuthority로 변환하는 메서드
    public List<GrantedAuthority> getAuthorities() {
        if (role == null || role.isEmpty()) {
            return Collections.emptyList();  // 역할이 없는 경우 빈 목록 반환
        }
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
        // 역할을 GrantedAuthority로 변환하여 반환
    }
}
