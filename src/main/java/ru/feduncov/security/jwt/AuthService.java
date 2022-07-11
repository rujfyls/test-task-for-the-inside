package ru.feduncov.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.feduncov.controller.dto.JwtRequestDTO;
import ru.feduncov.controller.dto.JwtResponseDTO;
import ru.feduncov.entity.User;
import ru.feduncov.service.UserService;

import javax.security.auth.message.AuthException;
//Класс для проверки пароля
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtResponseDTO login(JwtRequestDTO authRequest) throws AuthException {
        final User user = userService.getByLogin(authRequest.getLogin());

        if (user.getPassword().equals(authRequest.getPassword())) { // если пароль верен
            final String accessToken = jwtProvider.generateToken(user); //то генерируем токен
            return new JwtResponseDTO(accessToken); //возвращаем новый токен
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
