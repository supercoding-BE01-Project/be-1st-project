package com.be01.be_01_01.dashboard.repository;

import com.be01.be_01_01.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
