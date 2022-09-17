package com.macrame.demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //    @Value("${authorization.interceptor.allowed-url:/api/**}")
    private String[] interceptorAllowedURL = new String[]{"/api/**","/public/api/**"};


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        logger.debug("Configure webflux security.{}", Arrays.toString(interceptorAllowedURL));
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/actuator/**").hasRole("ACTUATOR")
                .pathMatchers(interceptorAllowedURL).permitAll()
                .and()
                //.addFilterAt(securityFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic().and().build();
    }
}
