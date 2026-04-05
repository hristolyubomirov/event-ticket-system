package com.example.event_ticket_system;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Entity
@Data
@Table(name="users_table" , schema = "users_schema")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Column(unique = true,nullable = false)
    @Email
    private String email;

    @Size(min=6, message="Password should have at least 6 characters.")
    @NotBlank
    @Getter
    private String password;

    @NotNull
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "pref_category")
    private String prefCategory;



}
