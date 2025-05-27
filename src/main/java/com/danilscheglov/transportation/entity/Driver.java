package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность водителя")
@Entity
@Table(name = "driver")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    @Schema(description = "Идентификатор водителя", example = "1")
    private Long id;

    @Column(name = "driver_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия водителя", example = "Петров")
    private String surname;

    @Column(name = "driver_name", nullable = false, length = 50)
    @Schema(description = "Имя водителя", example = "Петр")
    private String name;

    @Column(name = "driver_patronymic", length = 50)
    @Schema(description = "Отчество водителя", example = "Петрович")
    private String patronymic;

    @Column(name = "driver_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона водителя", example = "+79001234567")
    private String phone;

    @Column(name = "driver_email", nullable = false, length = 100)
    @Schema(description = "Email водителя", example = "petrov@example.com")
    private String email;

    @Column(name = "driver_password", nullable = false, length = 255)
    @Schema(description = "Пароль водителя")
    private String password;
}