package ru.feduncov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.feduncov.entity.Message;
import ru.feduncov.entity.User;
import ru.feduncov.exceptions.MessageException;
import ru.feduncov.repository.MessageRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    //Сохранить сообщение
    public void saveMessage(User user, String message) {
        messageRepository.save(new Message(message, user, LocalDateTime.now(ZoneId.systemDefault())));
    }

    //Получить определенное количество сообщений, количество передается через входной аргумент text
    public List<Message> getMessages(String text) {
        int count = Integer.parseInt(text.trim().substring(text.indexOf(' ') + 1));
        if (count == 0) {
            return List.of(new Message());
        }
        return messageRepository.findMessages(
                PageRequest.of(0, count, Sort.by("id").descending())).toList();
    }

    //Получить список сообщений определенного пользователя
    public List<Message> getMyMessages(User user) {
        return messageRepository.findMessageByUserId(user.getUserId()).orElseThrow(
                () -> new MessageException("Сообщения для пользователя с id=" + user.getUserId() + " не найдены"));
    }
}
