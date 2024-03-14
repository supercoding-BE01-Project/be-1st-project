package com.be01.be_01_01.dashboard.repository.User;


import com.be01.be_01_01.dashboard.repository.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User,Integer> {



    @Query("SELECT u FROM User u WHERE u.Author = :name AND u.email = :email")
    Optional<User> findByNameAndEmail(@Param("name") String name, @Param("email") String email);


    Optional<User> findByEmail(String username);
}

