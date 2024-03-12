package com.be01.be_01_01.dashboard.repository;

import com.be01.be_01_01.dashboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
