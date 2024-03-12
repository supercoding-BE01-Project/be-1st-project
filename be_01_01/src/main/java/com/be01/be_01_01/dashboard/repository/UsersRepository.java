package com.be01.be_01_01.dashboard.repository;

import com.be01.be_01_01.dashboard.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
