package com.example.userservice.controller;

import com.example.userservice.entity.Admin;
import com.example.userservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 엔드포인트
 */
@RestController
@RequiredArgsConstructor
public class AdminController {
    public static final String TOKEN_HEADER_KEY = "token";
    private final AdminService adminService;

    /**
     * 관리자 로그인
     * @param admin 관리자 로그인 요청값
     * @return header에 token값 추가해서 반환
     */
    @PostMapping("/admin/login")
    public ResponseEntity<Object> login(@RequestBody Admin admin) {
        String token = adminService.login(admin.getLoginId(), admin.getPassword());

        return ResponseEntity.ok()
                .header(TOKEN_HEADER_KEY, token)
                .build();
    }
}
