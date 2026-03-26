package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;
    @PostMapping("/register")
    public void createUser(@RequestBody UsersDTO usersDTO){
        usersService.createUser(usersDTO);
    }


    @GetMapping("/allusers")
        public List<Users> getAllUsers(){
            return usersService.getAllUsers();
        }

        @PatchMapping("/addcategory")
    public void setPrefCategory(@RequestParam String name,@RequestParam String changeCategory){
            usersService.setPrefCategory(name,changeCategory);
        }

        @DeleteMapping("/delete-user/{name}")
    public void deleteUser(@PathVariable String name){
        usersService.deleteUser(name);
        }
}
