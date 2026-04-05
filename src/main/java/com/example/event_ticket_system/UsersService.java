package com.example.event_ticket_system;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UsersDTO createUser(UsersDTO usersDTO){
       String email = usersDTO.getEmail();
       String name = usersDTO.getName();
       Role role = usersDTO.getRole();
        if(userRepository.findByEmail(email).isPresent()){
            throw new ResourceNotFoundException("User already registered. Please use another email.");
        }
        Users user = new Users();
        user.setEmail(email);
        user.setName(name);
        String encodePassword = passwordEncoder.encode(usersDTO.getPassword());
        user.setPassword(encodePassword);
        user.setRole(role);
        user.setPrefCategory(usersDTO.getPrefCategory());
       userRepository.save(user);
        UsersDTO responseDto = new UsersDTO();
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        responseDto.setPrefCategory(user.getPrefCategory());
        responseDto.setRole(role);
        responseDto.setPassword("*******");
       return responseDto;

    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users setPrefCategory(Long userId, String category){
        Users u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ID not found."));
        u.setPrefCategory(category);
        userRepository.save(u);
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));

    }

    public void deleteUser(Long userId) {
        Users u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userRepository.delete(u);
    }

    public Users getUser(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found. Invalid email."));

    }



}
