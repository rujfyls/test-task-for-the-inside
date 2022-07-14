<h1>Тестовое задание для компании ООО Инсайд</h1>


__________________________________________________

<h5>Данный проект представляет из себя сервис, который при авторизации пользователя создает токен (действует 1 час),
после чего он может: <br>

- Написать сообщение, которое сохранится в БД<br>
- Просматривать последние сообщения, количество он вводит сам <br>
- Просмотреть все свои сообщения</h5>
  Данный сервис работает на порту: 8888<br>

_**Запуск:**_  <br>
Перейти в корень проекта и набрать: <br>

_docker compose -f docker-compose.yml up_

---------------------------------------------------

**Возможности**
<br><br>

- **POST** method /api/auth/login (произвести авторизацию) <br>
**curl:** curl -H "Content-Type: application/json" -X POST http://localhost:8888/api/auth/login -d "{\"login\":\"alex\",\"password\":\"1234\"}"

<pre>
{
    "login": "alex",
    "password": "1234"
}</pre>

Ответ: <br>

<pre>
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGV4IiwiZXhwIjoxNjU3NTMwNjAxLCJmaXJzdE5hbWUiOiJhbGV4YW5kZXIifQ.FOf0IBSqA27zbu7VBMuNW6qHO1LmZhUdPw7Jzfgs8tSgUwWXpf9HMs50BtfswCPJfnAfOeHenPD7DLzxDG6xRg"
}</pre>

- **POST** method /api/message (сохранение сообщения) <br>
**curl:** curl -H "Content-Type: application/json"
  -H "Authorization: Bearer token"
  -X POST http://localhost:8888/api/message -d "{\"name\":\"Alexander\",\"message\":\"тестовое сообщение\"}"

<pre>
{
    "name": "Alexander",
    "message": "тестовое сообщение"
}</pre>

Ответ: <br>

<pre>
[
    {
        "message": "Сообщение успешно сохранено"
    }
]</pre>

- **POST** method /api/message (получить N последних сообщений) <br>
**curl**: curl -H "Content-Type: application/json"
  -H "Authorization: Bearer token"
  -X POST http://localhost:8888/api/message -d "{\"name\":\"Alexander\",\"message\":\"history 2\"}"

<pre>
{
    "name": "Alexander",
    "message": "history 2"
}</pre>
Ответ: <br>

<pre>
[
    {
        "message": "сообщение №16"
    },
    {
        "message": "сообщение №15"
    }
]</pre>

- **GET** method /api/message/auth (получение все свои сообщения) <br>
**curl**: curl -H "Authorization: Bearer token"
  -X GET http://localhost:8888/api/message/auth

Ответ: <br>

<pre>
[
    {
        "message": "сообщение №1"
    },
    {
        "message": "сообщение №2"
    }
]</pre>

--------------------------------------------------------


В данном проекте использованы следующие технологии:
- Spring Boot
- Spring Security + jjwt
- Flyway
- Hibernate
- PostgreSQL
- H2
- JUnit
- Mockito
- Docker