package com.danilscheglov.transportation.entity;

import com.danilscheglov.transportation.entity.common.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность клиента")
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @Column(name = "user_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия", example = "Сидоров")
    private String surname;

    @Column(name = "user_name", nullable = false, length = 50)
    @Schema(description = "Имя", example = "Сидор")
    private String name;

    @Column(name = "user_patronymic", length = 50)
    @Schema(description = "Отчество", example = "Сидорович")
    private String patronymic;

    @Column(name = "user_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона ", example = "+79001234567")
    private String phone;

    @Column(name = "user_email", nullable = false, length = 100, unique = true)
    @Schema(description = "Email", example = "sidorov@example.com")
    private String email;

    @Column(name = "user_password", nullable = false, length = 255)
    @Schema(description = "Пароль")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;
}
