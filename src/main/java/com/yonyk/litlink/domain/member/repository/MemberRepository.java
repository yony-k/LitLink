package com.yonyk.litlink.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByAuthIdAndProvider(String authId, String provider);
}
