package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member dto);

    List<Member> findAll();

    Member findById(Long id);
}
