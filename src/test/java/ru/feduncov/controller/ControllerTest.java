package ru.feduncov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.feduncov.Application;
import ru.feduncov.controller.dto.RequestDTO;
import ru.feduncov.entity.User;
import ru.feduncov.security.jwt.AuthService;
import ru.feduncov.security.jwt.JwtFilter;
import ru.feduncov.security.jwt.JwtProvider;
import ru.feduncov.service.MessageService;
import ru.feduncov.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    private static final String jwtSecret =
            "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhtklktXjXdkcyygs8mPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==";

    @Autowired
    private JwtProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.tokenProvider = new JwtProvider(jwtSecret);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @MockBean
    private MessageService messageService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    public void message() throws Exception {
        String token = tokenProvider.generateToken(
                new User(1, "alex", "alex", "alex", "alex"));
        assertNotNull(token);

        mockMvc.perform(post("/api/message").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RequestDTO("alex", "test"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getMyMessages() throws Exception {

        String token = tokenProvider.generateToken(
                new User(1, "alex", "alex", "alex", "alex"));
        assertNotNull(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/auth")
                .header("Authorization", token)).andExpect(status().isOk());
    }
}
