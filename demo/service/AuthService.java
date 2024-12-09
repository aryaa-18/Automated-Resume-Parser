package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String saveUser(RegisterDto registerDto);
}