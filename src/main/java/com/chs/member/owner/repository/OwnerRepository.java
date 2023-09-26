package com.chs.member.owner.repository;

import com.chs.member.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    boolean existsByUserId(String userId);

    Optional<Owner> findByUserId(String userId);
}
