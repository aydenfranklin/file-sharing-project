package com.macrame.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthenticationDto {
    @NotBlank(message = "Email is blank")
    @Email(message="Your email is invalid")
    private String email;
    @NotBlank(message = "Password is blank")
    @Size(max = 512, message="Encrypted password length should be 512")
    private String password;
}
