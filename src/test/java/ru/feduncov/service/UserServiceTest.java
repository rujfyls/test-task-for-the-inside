package ru.feduncov.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.feduncov.Application;
import ru.feduncov.exceptions.UserNotFoundException;
import ru.feduncov.repository.UserRepository;
import ru.feduncov.security.jwt.JwtFilter;
import ru.feduncov.security.jwt.JwtProvider;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WebMvcTest(UserService.class)
class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtProvider tokenProvider;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getUserByLogin() {
        when(userRepository.findUserByLogin("test")).thenReturn(Optional.empty());

        UserNotFoundException thrown =
                assertThrows(UserNotFoundException.class, () -> userService.getByLogin("test"));

        assertTrue(thrown.getMessage().contains("Пользователя с логином = test не существует!"));
    }
}