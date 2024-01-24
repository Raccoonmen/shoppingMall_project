package com.example.shopping.config;

import com.example.shopping.security.jwt.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;



// Spring Security에 대한 설정
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
                .cors()
                .and()
                .apply(authenticationManagerConfig)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .mvcMatchers( "/members/signup", "/members/login", "/members/refreshToken").permitAll()
                .mvcMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .anyRequest().hasAnyRole()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
