package com.hostalmanagement.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF (only for stateless APIs or testing purposes)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow public access to all endpoints
            )
            .formLogin(form -> form.disable()) // Disable the default login form
            .httpBasic(basic -> basic.disable()); // Optional: Disable basic authentication if not needed

        return http.build();
    }
}
