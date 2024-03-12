package com.be01.be_01_01.dashboard.repository;

import com.be01.be_01_01.dashboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findByUsersEmail(String email);
}
