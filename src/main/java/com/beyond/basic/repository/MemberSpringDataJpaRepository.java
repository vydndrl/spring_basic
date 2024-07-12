package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// /MemberRepository가 되기 위해서는 JpaRepository를 상속해야 하고, 상속시에 Entity 명과 entitiy의 PK타입을 명시
//MemberRepository 는 JpaRepository 를 상속함으로서 JpaRepository 의 주요 기능을 상속
//Jpa Repository 가 인터페이스임에도 해당 가능을 상속 해서 사용할 수 있는 이유는 hibernate 구현체가 미리 구현돼 있기 때문.
//런타임시점에 사용자가 인터페이스에 정의한 메서드를 리플렉션 기술을 통해 메서드를 구현
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
