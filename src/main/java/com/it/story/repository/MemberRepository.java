package com.it.story.repository;

import com.it.story.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);

    /**
     * 이메일 중복 여부를 확인
     *
     * @param email
     * @return true | false
     */
    boolean existsByEmail(String email);
}