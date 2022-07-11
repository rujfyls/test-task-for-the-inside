package ru.feduncov.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.feduncov.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findUserByNameAndLogin() {
        assertTrue(userRepository.findAll().iterator().hasNext());

        User user = userRepository.save(new User("login", "password", "имя", "фамилия"));

        entityManager.persist(user);
        entityManager.flush();

        assertEquals(userRepository.findUserByLogin("login").orElse(new User()).getLogin(), user.getLogin());
    }
}