package com.macrame.demo.service.impl;

import com.macrame.demo.dto.AuthenticationDto;
import com.macrame.demo.exception.AuthorizationException;
import com.macrame.demo.model.User;
import com.macrame.demo.repositories.jpa.UserRepository;
import com.macrame.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DatabaseUserService implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Override
    public Mono<String> signInWithEmail(AuthenticationDto authenticationDto, String userAgent) {
        //logger.debug("Try to login password: " + Encryptor.encodePassword(loginEntity.getPassword()));
        if (logger.isDebugEnabled()) {
            logger.debug("Try to login with password:{} ", authenticationDto.getPassword());
        }
        Optional<User> entityOptional = findOneByEmail(authenticationDto.getEmail());
        if (entityOptional.isPresent()) {
            User user = entityOptional.get();
            logger.debug("Found user: {}", user.getUserId());
            String rawPassword = authenticationDto.getPassword();
            //String securityPassword = null;
//            String rawPassword = null;
//            try {
//                rawPassword = encryptor.decryptWithRSA(encryptedPassword);
//                //securityPassword = encryptor.encryptWithMD5(password);
//                if (logger.isTraceEnabled()) {
//                    logger.trace("Try to login with the essential password: {}", rawPassword);
//                }
//
//            } catch (CryptographException e) {
//                throw new AuthorizationException(String.format("Could not decrypt the password for user : %s", authenticationDto.getEmail()), user.getUserId());
//            }
//            String salt = user.getSalt();
//            String hashedPassword = null;
//            try {
//                hashedPassword = encryptor.encryptWithPBKDF2(rawPassword, salt);
//            } catch (Exception e) {
//                logger.warn("Fail to encrypt password for user {}", user.getEmail());
//                throw new AuthorizationException(String.format("Could not encrypt the password for user : %s", authenticationDto.getEmail()), user.getUserId());
//            }
//            if (logger.isTraceEnabled()) {
//                logger.trace("Compare the passwords, stored password({}) to received password({}): for user {}", user.getPassword(), hashedPassword, authenticationDto.getEmail());
//            }
            if (rawPassword.equals(user.getPassword())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Check user status. Status : {} ", user.getStatus());
                }
//                if (!Constants.STATUS_NORMAL.equals(user.getStatus())) {
//                    throw new AuthorizationException(String.format("User: %s is inactive.", user.getUserId()), user.getUserId());
//                }


                return generateToken(user);
            } else {
                logger.warn("Fail to login for user {}, password does not match.", authenticationDto.getEmail());
                throw new AuthorizationException(String.format("Fail to login for user: %s", authenticationDto.getEmail()), user.getUserId());
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find this user {}", authenticationDto.getEmail());
            }
            throw new AuthorizationException("This user: " + authenticationDto.getEmail() + " has not been found.", 0L);
        }
    }

    @Override
    public Mono<String> signupWithEmail(AuthenticationDto authenticationDto) {
        if (logger.isDebugEnabled()) {
            logger.debug("Try to login with password:{} ", authenticationDto.getPassword());
        }
        Optional<User> entityOptional = findOneByEmail(authenticationDto.getEmail());
        if (entityOptional.isPresent()) {
            throw new AuthorizationException("This email: " + authenticationDto.getEmail() + " has been used.", 0L);
        } else {
            User user = new User();
            user.setUserId(1L);
            user.setEmail(authenticationDto.getEmail());
            user.setPassword(authenticationDto.getPassword());
            user.setStatus(1);
            user.setDescription("A new user");
            user.setSalt("ABDSE");
            user.setName(authenticationDto.getEmail().substring(0, authenticationDto.getEmail().indexOf("@")));
            userRepository.save(user);
            return generateToken(user);
        }
    }

    private Mono<String> generateToken(User user) {
        return Mono.just("ABC");
    }
    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

//    private Mono<String> generateToken1(User user) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Check token expiration time.");
//        }
//        Optional<String> encodedToken = tokenProcessor.generateToken(String.valueOf(user.getUserId()), user.getTokenVersion() + 1);
//        if (!encodedToken.isPresent()) {
//            throw new AuthorizationException("Fail to generate token", user.getUserId());
//        }
//        String token = encodedToken.get();
//        if (logger.isDebugEnabled()) {
//            logger.debug("encodedToken = {}", token);
//        }
//        LocalDateTime loginTime = LocalDateTime.now();
//        userRepository.updateLastLoginTimeByUserId(user.getUserId(), loginTime);
//
//
//        String tokenKey = USER_TOKEN_KEY_PREFIX + token;
//        String userTokenKey = USER_ID_KEY_PREFIX + user.getUserId();
//        ReactiveValueOperations<String, UserEntity> tokenOperations = reactiveRedisTemplate.opsForValue();
//        ReactiveSetOperations<String, String> reverseTokenOperations = reactiveRedisTemplate.opsForSet();
//        final UserEntity userEntity = new UserEntity(user.getUserId(), user.getEmail(), tokenExpirationSeconds);
//        return tokenOperations.set(tokenKey, userEntity, Duration.ofSeconds(tokenExpirationSeconds))
//                .then(reverseTokenOperations.add(userTokenKey, token))
//                .map(result -> token);
//    }
    private Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
