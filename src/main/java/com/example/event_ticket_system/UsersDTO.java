package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class UsersDTO {
        @NotBlank
        private String name;
        @NotBlank()
        private String email;
        @NotBlank()
        private String prefCategory;

    }


