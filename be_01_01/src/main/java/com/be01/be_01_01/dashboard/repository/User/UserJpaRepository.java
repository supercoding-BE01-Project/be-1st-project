package com.be01.be_01_01.dashboard.repository.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username);

}

