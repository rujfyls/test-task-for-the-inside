package ru.feduncov.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.feduncov.entity.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//Класс генерации и валидации токена
@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret
    ) {
        this.jwtSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret)); //преобразование обратно в массив байт
    }

    public String generateToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant expirationInstant = now.plusHours(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(expirationInstant);
        return Jwts.builder()
                .setSubject(user.getLogin()) //указывает логин пользователя
                .setExpiration(accessExpiration) //дату до которой токен валиден
                .signWith(jwtSecret) //алгоритм шифрования
                .claim("firstName", user.getFirstName()) //имя пользователя
                .compact();
    }

    public boolean validateAccessToken(String accessToken) { //отвечают за проверку валидности токена
        return validateToken(accessToken, jwtSecret);
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret) //устанавливает шифрование
                    .build()
                    .parseClaimsJws(token); //парсит токен согласно требованиям (секретного ключа, даты и имени)
            return true;
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtSecret);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
