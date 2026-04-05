package com.example.event_ticket_system;

import com.example.event_ticket_system.Auth.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class UsersControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsersService usersService;

    @Mock
    private AuthController authController;

    @InjectMocks
    private UsersController usersController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void registerUser() throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("email", "test2@test.com");
        data.put("password", "123456");
        data.put("role","ADMIN");
        data.put("name","Hristo");
        data.put("prefCategory","Movie");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());
    }


    @Test
    public void loginUser() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        Map<String, String> data = new HashMap<>();
        data.put("email", "test2@test.com");
        data.put("password", "123456");


        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());
    }
}