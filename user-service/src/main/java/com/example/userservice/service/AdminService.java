package com.example.userservice.service;

import com.example.userservice.entity.Admin;
import com.example.userservice.error.exception.NotFoundException;
import com.example.userservice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthTokenService tokenService;

    /**
     * 관리자 로그인
     * @param loginId 관리자 로그인 아이디
     * @param password 관리자 비밀번호
     * @return admin token
     */
    public String login(String loginId, String password) {
        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NotFoundException("not exist admin loginId", 1L));

        if (! passwordEncoder.matches(password, admin.getPassword())) {
            throw new NotFoundException("password missMatch", 1L);
        }

        return tokenService.createAdminToken(admin);
    }
}
