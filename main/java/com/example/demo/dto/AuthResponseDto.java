package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponseDto {

    private String accessToken;
    private String username;
    private String successMSG;

}