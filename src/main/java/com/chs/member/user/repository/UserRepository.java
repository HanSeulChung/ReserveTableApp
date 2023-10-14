package com.chs.member.user.repository;

import com.chs.member.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserIdAndUserName(String userId, String userName);

    Optional<User> findByResetPasswordKey(String resetPasswordKey);
}
