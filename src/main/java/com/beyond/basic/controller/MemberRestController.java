package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
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

//    @GetMapping("/member/detail/{id}")
//    public MemberDetailResDto memberDetail(@PathVariable Long id) {
//        return memberService.memberDetail(id);
//    }

    @GetMapping("/member/detail/{id}")
    public ResponseEntity<MemberDetailResDto> memberDetail(@PathVariable Long id) {
        try {
            MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
            return new ResponseEntity<>(memberDetailResDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
        }
        return memberService.memberDetail(id);
    }

    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            return "ok";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "error";
        }
    }
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

}
