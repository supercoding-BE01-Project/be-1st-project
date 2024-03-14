package com.be01.be_01_01.dashboard.service.security;

import com.be01.be_01_01.dashboard.repository.User.User;
import com.be01.be_01_01.dashboard.repository.User.UserJpaRepository;
import com.be01.be_01_01.dashboard.repository.userDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository; // User 데이터에 접근하기 위한 JPA 리포지토리입니다.


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름(이메일)을 기반으로 사용자 정보를 로드하는 메서드입니다.
        User user = userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        // 사용자 이메일로 검색하여 사용자를 찾습니다. 찾지 못하면 예외를 발생시킵니다.

        // 사용자의 권한 목록을 GrantedAuthority 리스트로 변환합니다.
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) authority)
                .collect(Collectors.toList());

        // CustomUserDetails 객체를 생성하여 반환합니다. 여기에 사용자 ID, 이메일, 비밀번호, 권한 목록을 설정합니다.
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return customUserDetails;
    }
}

//이 코드는 Spring Security의 UserDetailsService 인터페이스를 구현한 클래스입니다.
// loadUserByUsername 메서드는 사용자의 이름(이 경우 이메일 주소)을 받아 해당 사용자의 정보와 권한을 불러와 UserDetails 객체로 만들어 반환합니다.
// 이 과정에서 사용자 정보가 없을 경우 UsernameNotFoundException을 던집니다. 이 클래스는 Spring Security에서 사용자 인증 과정에서 사용자 정보를 불러오는 데 사용됩니다.
