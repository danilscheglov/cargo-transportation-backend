# Cargo Transportation System

Система управления грузоперевозками - это REST API приложение, разработанное для автоматизации процессов управления транспортной компанией.

## Технологии

- Java 17
- Spring Boot
- PostgreSQL
- Liquibase
- Swagger (OpenAPI)

## Требования

Для запуска приложения необходимо:

1. JDK 17 или выше
2. PostgreSQL (или Docker для запуска PostgreSQL в контейнере)
3. Maven 3.8 или выше

## Установка и запуск

1. Клонируйте репозиторий:

```bash
git clone https://github.com/DanilScheglov/cargo-transportation-backend.git
cd cargo-transportation-backend
```

2. Создайте базу данных в PostgreSQL.

   Вариант 1 - Локальная установка PostgreSQL:

   ```sql
   CREATE DATABASE transportation;
   ```

   Вариант 2 - Запуск PostgreSQL в Docker:

   ```bash
   # Запуск контейнера PostgreSQL
   docker run -d --name postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5432:5432 \
     -v postgres_data:/var/lib/postgresql/data \
     postgres:17-alpine

   # База данных transportation будет создана автоматически при первом запуске приложения
   ```

3. Настройте подключение к базе данных в `src/main/resources/application.properties`:

```properties
# Для локальной установки PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/transportation
spring.datasource.username=your_username
spring.datasource.password=your_password
# Для Docker установки (значения по умолчанию)
spring.datasource.url=jdbc:postgresql://localhost:5432/transportation
spring.datasource.username=postgres
spring.datasource.password=postgres
```

4. Запустите приложение:

```bash
mvn spring-boot:run
```

## Документация API

После запуска приложения документация API доступна по следующим URL:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## Поддержка

По вопросам и предложениям обращайтесь:

- GitHub: https://github.com/DanilScheglov
