package com.be01.be_01_01.dashboard.repository.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserEmail(String email);
}
