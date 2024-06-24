package com.example.userservice.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class TestSecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        http.csrf(csrfConfig -> csrfConfig.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/members", "POST")).permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }

//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//            web.ignoring().antMatchers("/api/auth/**"); // 테스트용 API의 Security 비활성화
//        }
}
