package com.it.story.service;

import com.it.story.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    /**
     * 모든 유저 리스트를 반환
     *
     * @return 유저 리스트
     */
    List<Member> findAll();

    /**
     * 이메일을 통해 유저 조회
     *
     * @param email
     * @return 조회된 유저
     */
    Optional<Member> findByEmail(String email);

    /**
     * 이름을 통해 유저 조회
     *
     * @param name
     * @return 조회된 유저
     */
    Optional<Member> findByName(String name);

//  /**
//   * Security Context에 존재하는 인증 정보를 통해 유저 정보 조회
//   * @return 조회된 유저
//   */
//  Optional<Member> getMyInfo();

    /**
     * 유저 정보 수정
     *
     * @param user    수정활 Member Entity
     * @param newInfo
     * @return 수정된 Member
     */
    Member updateUser(Member user, String newInfo);
}
