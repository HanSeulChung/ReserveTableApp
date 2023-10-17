package com.chs.member.owner.repository;

import com.chs.member.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    boolean existsByUserId(String userId);
    Optional<Owner> findByUserId(String userId);
    Optional<Owner> findByEmail(String email);
    Optional<Owner> findByUserIdAndUserName(String userId, String userName);

    Optional<Owner> findByResetPasswordKey(String resetPasswordKey);
}
