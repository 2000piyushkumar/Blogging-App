package com.scaler.blogapi.security.jwt;

import com.scaler.blogapi.security.authTokens.AuthTokenAuthenticationFilter;
import com.scaler.blogapi.security.authTokens.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


@Configuration
public class AppSecurityConfig {
    @Autowired private  AuthTokenService authTokenService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable()
                .authorizeRequests()
                        .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/blogs").permitAll()
                        .anyRequest().authenticated()
                        .and().addFilterBefore(new JWTAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                        .addFilterBefore(new AuthTokenAuthenticationFilter(authTokenService),AnonymousAuthenticationFilter.class)
                        .httpBasic().and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
