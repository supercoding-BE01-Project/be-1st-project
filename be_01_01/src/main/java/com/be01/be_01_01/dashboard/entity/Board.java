package com.be01.be_01_01.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

}
