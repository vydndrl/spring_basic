package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

// 싱글톤
@Controller
//@RequiredArgsConstructor
public class MemberController {

//    의존성 주입 (DI) 방법 1. 생성자 주입 방식 (가장 많이 사용하는 방식)
//    장점 : 1) final 을 통해 상수로 사용 가능 2) 다형성 구현 가능 3) 순환 참조 방지
//    생성자가 1개 밖에 없을 경우에는 Autowired 생략 가능
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
//    의존성 주입 방법 2. 필드 주입 방식(Autowired 만 사용)
//    final 선언 불가 -> 안정성이 떨어짐
//    @Autowired
//    private MemberService memberService;

//    의존성 주입 방법 3. 어노테이션(RequiredArgs)을 이용한 방식
//    @RequiredArgsConstructor : @NotNull 어노테이션, final 키워드가 붙어있는 필드를
//    대상으로 생성자를 생성
//    private final MemberService memberService;


//    회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }
    
//    회원상세조회 : memberDetail
//    url(uri): member/1, member/2
//    화면명 : member-detail
    @GetMapping("/member/detail/{id}")
//    int 또는 long 받을 경우 스프링에서 형변환(String -> Long)
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetailResDto);
        return "/member/member-detail";
    }
    
//    회원가입화면 주고
//    url : members/create
//    화면명 : member-create
    @GetMapping("/member/create")
    public String memberCreate() {
        return "/member/member-create";
    }
    
//    회원가입데이터를 받는다
//    url: member/create
//    name, email, password
//    회원가입이 완료되면 다른 화면으로 이동시키기 (@ResponseBody 제거)
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto dto, Model model) {
        try {
            memberService.memberCreate(dto);
            // 화면 리턴이 아닌, url 재호출
            return "redirect:/member/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }

    }

    @GetMapping("/")
    public String home() {
        return "/member/home";
    }

}
