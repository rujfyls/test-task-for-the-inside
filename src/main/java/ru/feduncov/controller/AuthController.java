package ru.feduncov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.feduncov.security.jwt.AuthService;
import ru.feduncov.controller.dto.JwtRequestDTO;
import ru.feduncov.controller.dto.JwtResponseDTO;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO authRequest) throws AuthException {
        final JwtResponseDTO token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }
}
