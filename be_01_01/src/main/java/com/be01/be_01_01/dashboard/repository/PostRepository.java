package com.be01.be_01_01.dashboard.repository;

import com.be01.be_01_01.dashboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserEmail(String email);
}
