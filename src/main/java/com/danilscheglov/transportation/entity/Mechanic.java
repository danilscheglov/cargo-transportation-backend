package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность механика")
@Entity
@Table(name = "mechanic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanic_id")
    @Schema(description = "Идентификатор механика", example = "1")
    private Long id;

    @Column(name = "mechanic_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия механика", example = "Петров")
    private String surname;

    @Column(name = "mechanic_name", nullable = false, length = 50)
    @Schema(description = "Имя механика", example = "Петр")
    private String name;

    @Column(name = "mechanic_patronymic", length = 50)
    @Schema(description = "Отчество механика", example = "Петрович")
    private String patronymic;

    @Column(name = "mechanic_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона механика", example = "+79001234567")
    private String phone;

    @Column(name = "mechanic_email", nullable = false, length = 100)
    @Schema(description = "Email механика", example = "petr@example.com")
    private String email;

    @Column(name = "mechanic_password", nullable = false, length = 255)
    @Schema(description = "Пароль механика")
    private String password;
}