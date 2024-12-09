package com.example.demo.dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Data
public class LoginDto {
    private String username;
    private String password;
}
