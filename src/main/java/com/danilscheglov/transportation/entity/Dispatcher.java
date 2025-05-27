package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность диспетчера")
@Entity
@Table(name = "dispatcher")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dispatcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispatcher_id")
    @Schema(description = "Идентификатор диспетчера", example = "1")
    private Long id;

    @Column(name = "dispatcher_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия диспетчера", example = "Иванов")
    private String surname;

    @Column(name = "dispatcher_name", nullable = false, length = 50)
    @Schema(description = "Имя диспетчера", example = "Иван")
    private String name;

    @Column(name = "dispatcher_patronymic", length = 50)
    @Schema(description = "Отчество диспетчера", example = "Иванович")
    private String patronymic;

    @Column(name = "dispatcher_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона диспетчера", example = "+79001234567")
    private String phone;

    @Column(name = "dispatcher_email", nullable = false, length = 100)
    @Schema(description = "Email диспетчера", example = "ivanov@example.com")
    private String email;

    @Column(name = "dispatcher_password", nullable = false, length = 255)
    @Schema(description = "Пароль диспетчера")
    private String password;
}