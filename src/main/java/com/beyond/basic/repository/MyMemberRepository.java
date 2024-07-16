package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> { // <엔티티명, pk 타입>
    Optional<Member> findByEmail(String email);
    List<Member> findByName(String name);

    // jpql 문법을 통한 raw쿼리 작성시 컴파일 타임에서 오류 체크
    @Query("select m from Member m")
    List<Member> myFindAll();
}