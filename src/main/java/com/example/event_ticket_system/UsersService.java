package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    public Users createUser(String name,String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User already registered. Please use another email.");
        }
        Users user = new Users();
        user.setEmail(email);
        user.setName(name);
        return userRepository.save(user);

    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

}
