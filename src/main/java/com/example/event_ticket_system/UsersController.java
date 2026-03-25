package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;
    @PostMapping("/register")
    public void createUser(String name, String email){
        usersService.createUser(name,email);
    }


    @GetMapping("/allusers")
        public List<Users> getAllUsers(){
            return usersService.getAllUsers();
        }
}
