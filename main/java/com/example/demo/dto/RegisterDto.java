package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class RegisterDto {

    private String name;
    private String email;
    private String username;
    private String password;

}