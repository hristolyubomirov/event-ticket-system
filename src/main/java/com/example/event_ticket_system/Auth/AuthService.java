package com.example.event_ticket_system.Auth;

import com.example.event_ticket_system.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

        public AuthService (UsersService usersService, PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
            this.usersService = usersService;
            this.passwordEncoder =passwordEncoder;
            this.jwtUtil = jwtUtil;
        }



    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){

        Optional<String> token = Optional.ofNullable(usersService.getUser(loginRequestDTO.getEmail()))
        .filter(usr -> passwordEncoder.matches(loginRequestDTO.getPassword(), usr.getPassword()))
                .map(usr -> jwtUtil.generateToken(usr.getEmail(),usr.getRole().toString()));
        System.out.println("LOGIN REQUEST DTO: " + loginRequestDTO.getPassword());
        System.out.println("USER PASS: " + usersService.getUser(loginRequestDTO.getEmail()).getPassword());
        return token;
        }


}
