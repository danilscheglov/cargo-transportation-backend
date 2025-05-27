package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность оператора")
@Entity
@Table(name = "operator")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operator_id")
    @Schema(description = "Идентификатор оператора", example = "1")
    private Long id;

    @Column(name = "operator_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия оператора", example = "Иванов")
    private String surname;

    @Column(name = "operator_name", nullable = false, length = 50)
    @Schema(description = "Имя оператора", example = "Иван")
    private String name;

    @Column(name = "operator_patronymic", length = 50)
    @Schema(description = "Отчество оператора", example = "Иванович")
    private String patronymic;

    @Column(name = "operator_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона оператора", example = "+79001234567")
    private String phone;

    @Column(name = "operator_email", nullable = false, length = 100)
    @Schema(description = "Email оператора", example = "ivan@example.com")
    private String email;

    @Column(name = "operator_password", nullable = false, length = 255)
    @Schema(description = "Пароль оператора")
    private String password;
}