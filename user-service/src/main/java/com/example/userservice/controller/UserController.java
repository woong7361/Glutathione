package com.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment environment;

    @GetMapping("health-check")
    public String healthCheck() {
        log.info("health-check");

        return "good";
    }

    @GetMapping("config-test")
    public String configTest() {
        String secret = environment.getProperty("token.secret");
        log.info(secret);

        return secret;
    }
}
