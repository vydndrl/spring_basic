package com.beyond.basic.service;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberJdbcRepository;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// input 값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired // 싱글톤 객체를 주입(DI) 받는다
    public MemberService(MemberJdbcRepository memoryRepository) {
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

    public Member memberDetail(Long id) {
        return memberRepository.findById(id);
    }

    // memberList의 요소들을 for문으로 꺼내서 dto에 넣은 뒤 반환
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberResDtos =  new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            MemberResDto dto = new MemberResDto();
            dto.setName(member.getName());
            dto.setEmail(member.getEmail());
            memberResDtos.add(dto);
        }
        return memberResDtos;
    }
}
