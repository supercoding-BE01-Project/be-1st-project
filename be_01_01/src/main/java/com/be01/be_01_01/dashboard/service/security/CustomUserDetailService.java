package com.be01.be_01_01.dashboard.service.security;

import com.be01.be_01_01.dashboard.repository.user.User;
import com.be01.be_01_01.dashboard.repository.user.UserJpaRepository;
import com.be01.be_01_01.dashboard.repository.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                // 여기서 user.getRole()이 반환하는 단일 문자열을 리스트로 변환합니다.
                // 이전 코드에서는 user::getRoles와 같은 부분이 잘못되었으며, 실제로는 단일 role 문자열을 처리해야 합니다.
                .authorities(Collections.singletonList(user.getRole())) // 단일 role을 리스트로 변환
                .build();
        return customUserDetails;
    }
}
