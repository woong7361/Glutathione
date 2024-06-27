package com.example.productservice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductController {

    @GetMapping("/heath-check")
    public String healthCheck() {
        log.info("healthy");

        return "healthy";
    }
}
