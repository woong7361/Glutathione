package com.example.userservice.dummy;

import com.example.userservice.entity.Admin;
import com.example.userservice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 더미 데이터를 생성해주는 Initializer
 * TODO 배포 환경에서는 없어져야한다.
 */
@Component
@RequiredArgsConstructor
public class PostInitializer {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * 관리자 초기값 생성
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        Admin admin = Admin.builder()
                .adminName("ADMIN")
                .loginId("admin")
                .password("1234")
                .build();

        admin.encodePassword(passwordEncoder);
        adminRepository.save(admin);
    }
}
