package com.example.appjwtrealemailauditing.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class   LoginDto {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
