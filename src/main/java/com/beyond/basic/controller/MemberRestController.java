package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.MemberRepository;
import com.beyond.basic.repository.MyMemberRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
// RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
@Api(tags = "회원 관리 서비스")
public class MemberRestController {

    private final MemberService memberService;
    private final MyMemberRepository memberRepository;

    @Autowired
    public MemberRestController(MemberService memberService, MyMemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/member/text")
    public String memberText() {
        return "ok";
    }

//    @GetMapping("/member/list")
//    public List<MemberResDto> memberList() {
//        return memberService.memberList();
//    }
//
    @GetMapping("/member/list")
    public ResponseEntity<List<MemberResDto>> memberList() {
        List<MemberResDto> members = memberService.memberList();
        return new ResponseEntity<>(members, HttpStatus.CREATED);
    }

    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id) {
//        try {
            MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is found", memberDetailResDto);
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
//            return new ResponseEntity<>(commonErrorDto, HttpStatus.NOT_FOUND);
        }
//    }


    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto) {
//        try {
            memberService.memberCreate(dto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", null);
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
        }
//    }
//    수정은 2가지 요청 방식 : PUT, PATCH 요청
//    patch 요청 : 부분 수정, put 요청 : 덮어쓰기
    @PatchMapping("/member/pw/update")
    public String memberList(@RequestBody MemberUpdateDto dto) {
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id) {
        memberService.delete(id);
        return "ok";
    }

//    lazy(지연로딩), eager(즉시로딩) 테스트
    @GetMapping("member/post/all")
    public void memberPostAll() {
        List<Member> memberList = memberRepository.findAll();
        for (Member m : memberList) {
            System.out.println(m.getPosts().size());
        }
    }

}
