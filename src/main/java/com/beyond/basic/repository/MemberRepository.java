package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member dto);

    List<Member> findAll();

    Optional<Member> findById(Long id);
}
