package com.example.event_ticket_system;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import jakarta.validation.constraints.NotBlank;

@Data
public class UsersDTO {
        @NotBlank
        private String name;
        @NotBlank()
        private String email;
        @NotBlank()
        private String prefCategory;
        @NotBlank()
        @Size(min=6, message="Password must be at least 6 symbols.")
        private String password;
        @NotNull
        @Getter
        private Role role;
    }


