package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//@Data
@Getter
//entity 어노테이션을 통해 엔티티 매니저에게 객체 관리를 위임
//해당 클래스명으로 테이블 및 컬럼을 자동 생성하고 각종 설정 정보 위임
@Entity
@NoArgsConstructor // 기본 생성자는 JPA에서 필수
public class Member extends BaseEntity{
    @Id // pk 설정
//    identity :  auto_increment 설정
//    auto : Jpa한테 자동으로 적절한 전략을 선택하도록 맡기는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // Long은 bigint로 변환
//    String은 varchar(255)로 기본으로 변환, name 변수명이 name 컬럼명으로 변환.
    private String name;
//    nullable = false : Not null 제약조건
    @Column(nullable = false, unique = true, length = 50)
    private String email;
//    @Column(name = "pw") 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있음
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

//    password 상단에 @Setter를 통해 특정 변수만 setter 사용이 가능하나,
//    일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장
    public void updatePw(String password) {
        this.password = password;
    }

    public MemberDetailResDto detFromEntity() {
        LocalDateTime createdTime = this.getCreatedTime();
        String value = createdTime.getYear() + "년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일 ";
        return new MemberDetailResDto(this.id, this.name, this.email,
                this.password, value);
    }

    public MemberResDto listFromEntity() {
        return new MemberResDto(this.id, this.name, this.email);
    }
}
