package com.be01.be_01_01.dashboard.service;


import com.be01.be_01_01.dashboard.repository.User.User;
import com.be01.be_01_01.dashboard.repository.User.UserJpaRepository;
import com.be01.be_01_01.dashboard.dto.UserPostsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserJpaRepository userJpaRepository;

    //전체 회원 정보 확인
    public List<UserPostsDTO> findAllUserInfos() {
        List<User> userPostsDTOS = userJpaRepository.findAll();
        List<UserPostsDTO> userInfos = userPostsDTOS.stream().map(UserPostsDTO::new).collect(Collectors.toList());
        return userInfos;
    }

}
