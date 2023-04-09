package com.it.story.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Entity 어노테이션을 클래스에 선언하면 그 클래스는 JPA가 관리
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member extends CoreEntity {
    @Id // 이 프로퍼티가 pk 역할을 한다는 것을 명시
    @Column(name = "id") // 객체 필드와 DB 테이블 컬럼을 맵핑
    @GeneratedValue(strategy= GenerationType.IDENTITY) // @GeneratedValue는 pk의 값을 위한 자동 생성 전략을 명시하는데 사용
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false, unique = true)
    private String name;
//  @Enumerated(EnumType.STRING)
//  private UserRole role;

    @Builder
    public Member(String email, String password, String name /*UserRole role*/) {
        this.email = email;
        this.password = password;
        this.name = name;
//    this.role = role;
    }

    // https://reflectoring.io/spring-security-password-handling/
    /**
     * 비밀번호를 암호화
     * @param passwordEncoder 암호화 할 인코더 클래스
     * @return 변경된 유저 Entity
     */
    public Member hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
}