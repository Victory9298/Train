package com.example.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    @Autowired
    JWTAuthFilter jwtFilter;

    @Bean
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // как это использовать? не импортируется
//                .antMatchers("/openapi/**")
                .anyRequest()
                .permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(
                jwtFilter, UsernamePasswordAuthenticationFilter.class
        );
    }
}
