package com.example.userservice.controller;

import com.example.userservice.entity.Temp;
import com.example.userservice.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment environment;
    private final TempRepository tempRepository;

    @GetMapping("/home-page")
    public String homePage() {
        log.info("welcome home page " + LocalDateTime.now());

        return "welcome home";
    }

    @GetMapping("health-check")
    public String healthCheck() {
        log.info("health-check");

        return "good";
    }

    @GetMapping("config-test")
    public String configTest() {
//        String secret = environment.getProperty("token.secret");
//        log.info(secret);

//        return secret;
        return "";
    }

    @GetMapping("save-test")
    public String saveTest() {
        Temp temp = new Temp(null, UUID.randomUUID().toString());
        tempRepository.save(temp);

        return temp.getMemo();
    }
}
