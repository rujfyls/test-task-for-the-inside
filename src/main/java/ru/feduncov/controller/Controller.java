package ru.feduncov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.feduncov.controller.dto.RequestDTO;
import ru.feduncov.controller.dto.ResponseDTO;
import ru.feduncov.entity.User;
import ru.feduncov.security.jwt.AuthService;
import ru.feduncov.security.jwt.JwtAuthentication;
import ru.feduncov.service.MessageService;
import ru.feduncov.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final AuthService authService;
    private final UserService userService;
    private final MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<List<ResponseDTO>> message(@RequestBody RequestDTO requestDTO) {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo.isAuthenticated()) { //если прошел аутентификацию
            if (requestDTO.getMessage().matches("^history [0-9]+$")) {
                return ResponseEntity.ok(messageService.getMessages(requestDTO.getMessage()).stream()
                        .map(m -> new ResponseDTO(m.getText())).collect(Collectors.toList()));
            }
            User user = userService.getByLogin(authInfo.getUsername().toLowerCase());
            messageService.saveMessage(user, requestDTO.getMessage());
            return ResponseEntity.ok(List.of(new ResponseDTO("Сообщение успешно сохранено")));
        } else {
            return ResponseEntity.ok(List.of(new ResponseDTO("Обновите токен")));
        }
    }

    @GetMapping("/message/auth")
    public ResponseEntity<List<ResponseDTO>> getMyMessages() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (authInfo.isAuthenticated()) {
            User user = userService.getByLogin(authInfo.getUsername().toLowerCase());
            return ResponseEntity.ok(messageService.getMyMessages(user).stream()
                    .map(m -> new ResponseDTO(m.getText())).collect(Collectors.toList()));
        } else {
            return ResponseEntity.ok(List.of(new ResponseDTO("Обновите токен")));
        }
    }
}
