package com.chs.member.repository;

import com.chs.member.entity.Member;
import com.chs.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);
}
