package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    //TODO 제약조건 추가 필요 - 아이디의 길이, 패스워드 길이, 제한된 문자열 ...
    @Column(unique = true, nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;


    /**
     * 비밀번호 encoding
     * @param passwordEncoder 인코딩할 인코더(인코딩 알고리즘&key 포함)
     */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 회원정보 업데이트
     * @param updateMember 회원정보 업데이트 요청 파라미터
     */
    public void update(Member updateMember) {
        this.loginId = updateMember.getLoginId();
        this.password = updateMember.getPassword();
        this.memberName = updateMember.getMemberName();
    }

    /**
     * 회원 삭제 표시
     */
    public void delete() {
        this.isDelete = true;
    }
}
