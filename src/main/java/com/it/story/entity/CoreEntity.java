package com.it.story.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
/**
 * JPA Entity 클래스들이 CoreEntity 추상 클래스를 상속할 경우
 * createDate, modifiedDate를 컬럼으로 인식하도록
 * MappedSuperclass 어노테이션을 추가
 */
@MappedSuperclass
/**
 * Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능인
 * JPA Audit를 사용하기 위해 아래 줄을 통해
 * CoreEntity 클래스에 Auditing 기능을 포함
 *
 * 그리고
 * 스프링 부트의 Entry 포인트 클래스에
 * @EnableJpaAuditing 어노테이션을 적용하여 JPA Auditing을 활성화
 */
@EntityListeners({AuditingEntityListener.class})
public class CoreEntity {
    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updateAt;
}
