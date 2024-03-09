package com.be01.be_01_01.dashboard.repository.user;

import com.be01.be_01_01.dashboard.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Integer> {

}
