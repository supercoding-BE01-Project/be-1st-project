package com.be01.be_01_01.dashboard.repository.comment;

import com.be01.be_01_01.dashboard.repository.board.Board;
import com.be01.be_01_01.dashboard.repository.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content",nullable = false, columnDefinition = "TEXT",length = 255) // VARCHAR 글자 제한 추가
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime localDateTime;

}
