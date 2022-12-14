package com.jschwery.securitydemo.repository;

import com.jschwery.securitydemo.entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenVerification extends JpaRepository<UserToken, Long> {
}
