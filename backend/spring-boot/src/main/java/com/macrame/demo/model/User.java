package com.macrame.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Where(clause = "status != -1")
public class User {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;                            // user ID
    private String name;                            // username
    private String email;                           // email
    @JsonIgnore
    private String password;                        // password
    @JsonIgnore
    private String salt;                            // salt for each user
    private String description;                     // user description
    private Integer status = Integer.valueOf(1);  // status 1=normal 0=disabled -1=deleted
    private Integer tokenVersion;                   // version of token
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdTime;                         // user created date

}
