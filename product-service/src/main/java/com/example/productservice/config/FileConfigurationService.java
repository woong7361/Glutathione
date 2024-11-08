package com.example.productservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 파일 관련 환경변수 서비스
 */
@Component
@RequiredArgsConstructor
public class FileConfigurationService {
    private final Environment environment;

    /**
     * 파일 경로 반환
     * @return 파일 경로
     */
    public String getPath() {
        return environment.getProperty("file.path");
    }
}
