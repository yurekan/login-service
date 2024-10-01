package com.ecommerce.login_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // You can customize this as needed
                .authorizeHttpRequests(authz ->
                        authz.anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}); // Configure basic auth as needed

        return http.build();
    }
}
