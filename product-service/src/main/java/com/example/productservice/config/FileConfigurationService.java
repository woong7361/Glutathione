package com.example.productservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileConfigurationService {
    private final Environment environment;

    public String getPath() {
        return environment.getProperty("file.path");
    }
}
