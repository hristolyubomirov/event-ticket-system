package com.example.event_ticket_system.Auth;
public class LoginResponseDTO {
    private final String token;

    public LoginResponseDTO(String token){
        this.token = token;

    }

    public String getToken(){
        return token;
    }
}
