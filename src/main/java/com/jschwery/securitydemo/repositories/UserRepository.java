package com.jschwery.securitydemo.repositories;

import com.jschwery.securitydemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
     Optional<User> findByUsername(String username);
     Optional<User> findByEmail(String email);

}


