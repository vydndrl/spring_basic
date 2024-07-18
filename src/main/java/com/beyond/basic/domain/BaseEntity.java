package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
//기본적으로 Entity는 상속 관계가 불가능하여,
//해당 어노테이션을 붙여야 상속 관계 성립 가능
@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp 
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

}
