package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String prefCategory;

}
