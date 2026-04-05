package com.example.event_ticket_system;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;

import java.util.List;

@RestController
@Validated
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public UsersDTO createUser(@Valid @RequestBody UsersDTO usersDTO) {
        return usersService.createUser(usersDTO);

    }


    @GetMapping("/allusers")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @PatchMapping("/addcategory")
    public Users setPrefCategory(@NotNull @Positive @RequestParam Long userId,@Valid @RequestParam String changeCategory){
        return usersService.setPrefCategory(userId,changeCategory);

    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(@NotNull @Positive @PathVariable Long userId){
        usersService.deleteUser(userId);
    }


    @GetMapping("/user")
public Users getUser(@RequestBody @Email String email){
    return usersService.getUser(email);
    }


}
