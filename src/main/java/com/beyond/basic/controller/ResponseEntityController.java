package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.CommonResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
//    case1. @ResponseStatus 어노테이션 방식

    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1() {
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member annotation2() {
//        (가정)객체 생성 후 DB 저장 성공
        Member member = new Member("hong", "hong@naver.com", "1234");
        return member;
    }
    
//    case2. 메서드 체이닝 방식 : ResponseEntity의 클래스 메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3() {
        return ResponseEntity.notFound().build();
    }

//    case3. ResponseEntity 객체를 직접 custom 하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2() {
        Member member = new Member("hong", "hong@naver.com", "1234");
        CommonResDto commonResDto = new CommonResDto
                (HttpStatus.CREATED, "member is successfully created", member);
//        여기 created는 헤더에 들어가고 위 created는 바디에 들어감
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }


}
