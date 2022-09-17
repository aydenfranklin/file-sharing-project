package com.macrame.demo.service;

import com.macrame.demo.dto.AuthenticationDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> signInWithEmail(AuthenticationDto authenticationDto, String userAgent);

    Mono<String> signupWithEmail(AuthenticationDto authenticationDto);
}
