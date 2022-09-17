package com.macrame.demo.repositories.jpa;

import com.macrame.demo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User SET lastLoginTime = ?2 WHERE userId = ?1")
    void updateLastLoginTimeByUserId(Long userId, LocalDateTime loginTime);
}
