package ru.feduncov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.feduncov.entity.User;
import ru.feduncov.exceptions.UserNotFoundException;
import ru.feduncov.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //Получить пользователя по логину
    public User getByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с логином = " + login + " не существует!"));
    }
}
