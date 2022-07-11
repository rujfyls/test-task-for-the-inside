package ru.feduncov.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.feduncov.entity.Message;
import ru.feduncov.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findMessageByUserId() {
        assertTrue(messageRepository.findAll().iterator().hasNext());

        User user = userRepository.save(new User("login", "password", "имя", "фамилия"));
        Message message = messageRepository.save(new Message("text", user, LocalDateTime.now()));


        entityManager.persist(user);
        entityManager.persist(message);
        entityManager.flush();


        String expected = messageRepository.findMessageByUserId(user.getUserId()).get().get(0).getText();

        assertEquals(expected, message.getText());
    }
}
