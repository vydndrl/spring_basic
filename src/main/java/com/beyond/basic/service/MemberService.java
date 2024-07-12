package com.beyond.basic.service;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// input 값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
// 트랜잭셔널 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고, (각 메서드마다 하나의 트랜잭션으로 묶는다는 뜻)
// 만약 예외가 발생 시 롤백 처리 자동화
@Transactional
public class MemberService {
////    다형성 설계
//    private final MemberRepository memberRepository;
//
//    @Autowired // 싱글톤 객체를 주입(DI) 받는다
//    public MemberService(MemberSpringDataJpaRepository memoryRepository) {
//        this.memberRepository = memoryRepository;
//    }

//    비다형성 설계
private final MyMemberRepository memberRepository;

    @Autowired // 싱글톤 객체를 주입(DI) 받는다
    public MemberService(MyMemberRepository memoryRepository) {
        this.memberRepository = memoryRepository;
    }
    
    public void memberCreate(MemberReqDto dto) {
        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setPassword(dto.getPassword());
        member.setEmail(dto.getEmail());
        memberRepository.save(member);
    }

    public MemberDetailResDto memberDetail(Long id) {
        MemberDetailResDto resDto = new MemberDetailResDto();
        Optional<Member> optMember = memberRepository.findById(id);
//        클라이언트에게 적절한 예외 메시지와 상태 코드를 주는 것이 주요 목적
//          또한, 예외를 강제 발생시킴으로서 적절한 롤백처리 하는것도 주요 목적
        Member member = optMember.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        resDto.setId(member.getId());
        resDto.setName(member.getName());
        resDto.setEmail(member.getEmail());
        resDto.setPassword(member.getPassword());
        return resDto;
    }

    // memberList의 요소들을 for문으로 꺼내서 dto에 넣은 뒤 반환
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberResDtos =  new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            MemberResDto dto = new MemberResDto();
            dto.setId(member.getId());
            dto.setName(member.getName());
            dto.setEmail(member.getEmail());
            memberResDtos.add(dto);
        }
        return memberResDtos;
    }
}
