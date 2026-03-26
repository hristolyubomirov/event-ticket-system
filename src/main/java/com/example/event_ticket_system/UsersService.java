package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    public Users createUser(UsersDTO usersDTO){
       String email = usersDTO.getEmail();
       String name = usersDTO.getName();
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User already registered. Please use another email.");
        }
        Users user = new Users();
        user.setEmail(email);
        user.setName(name);
        user.setPrefCategory(usersDTO.getPrefCategory());
        return userRepository.save(user);

    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public void setPrefCategory(String name, String category){
        Users u = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User name not found."));
        u.setPrefCategory(category);
        userRepository.save(u);

    }

    public void deleteUser(String name) {
        Users u = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found."));
        userRepository.delete(u);
    }
}
