package com.be01.be_01_01.dashboard.service;

import com.be01.be_01_01.dashboard.dto.DashboardPostsDTO;
import com.be01.be_01_01.dashboard.entity.Board;
import com.be01.be_01_01.dashboard.entity.BoardRepository;
import com.be01.be_01_01.dashboard.entity.Users;
import com.be01.be_01_01.dashboard.entity.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public DashboardService(BoardRepository boardRepository, UsersRepository usersRepository) {
        this.boardRepository = boardRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Board createBoard(DashboardPostsDTO dashboardPostsDTO) {

        Users users = usersRepository.findById(dashboardPostsDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. ID : " + dashboardPostsDTO.getUserId()));

        // 게시글 작성
        Board board = new Board();
        board.setTitle(dashboardPostsDTO.getTitle());
        board.setContent(dashboardPostsDTO.getContent());
        board.setUsers(users);

        return boardRepository.save(board);
    }
}
