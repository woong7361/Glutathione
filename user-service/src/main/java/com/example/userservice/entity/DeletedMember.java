package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeletedMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deletedMemberId;

    @Column(unique = true, nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String memberName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
