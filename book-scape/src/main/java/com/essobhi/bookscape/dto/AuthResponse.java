package com.essobhi.bookscape.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String fullName;
    private String email;
    private String token;
}
