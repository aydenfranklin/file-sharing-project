package com.macrame.demo.api;

import com.macrame.demo.dto.AuthenticationDto;
import com.macrame.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController {
    private static final String URL_API_PREFIX = "/api";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String URL_PUBLIC_PREFIX = "/public";
    @Autowired
    private UserService userService;
    // The spring bean container will give you such an instance

    //  /public/api/user/session
    @PostMapping(URL_PUBLIC_PREFIX + URL_API_PREFIX + "/user/session")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> signInWithEmail(@RequestHeader(name = "User-Agent") String userAgent, @RequestBody @Valid AuthenticationDto authenticationDto){

        if (logger.isInfoEnabled())
            logger.info("Request /public/api/user/session [POST]");
        logger.info("Attempt to sign in with email = {}, password = ***************", authenticationDto.getEmail());
        return userService.signInWithEmail(authenticationDto, userAgent);

    }
    @PostMapping(URL_PUBLIC_PREFIX + URL_API_PREFIX + "/user")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> signupWithEmail(@RequestBody @Valid AuthenticationDto authenticationDto){

        if (logger.isInfoEnabled())
            logger.info("Request /public/api/user [POST]");
        logger.info("Attempt to sign up with email = {}, password = ***************", authenticationDto.getEmail());
        return userService.signupWithEmail(authenticationDto);

    }
}
