package ru.feduncov.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.feduncov.Application;
import ru.feduncov.entity.User;
import ru.feduncov.exceptions.MessageException;
import ru.feduncov.repository.MessageRepository;
import ru.feduncov.security.jwt.JwtFilter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WebMvcTest(MessageService.class)
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getMyMessages() {
        User user = new User();
        user.setUserId(999);
        when(messageRepository.findMessageByUserId(user.getUserId())).thenReturn(Optional.empty());

        MessageException thrown =
                assertThrows(MessageException.class, () -> messageService.getMyMessages(user));

        assertTrue(thrown.getMessage().contains("Сообщения для пользователя с id=999 не найдены"));
    }
}
