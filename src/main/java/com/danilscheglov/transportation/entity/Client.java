package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность клиента")
@Entity
@Table(name = "client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    @Schema(description = "Идентификатор клиента", example = "1")
    private Long id;

    @Column(name = "client_surname", nullable = false, length = 50)
    @Schema(description = "Фамилия клиента", example = "Сидоров")
    private String surname;

    @Column(name = "client_name", nullable = false, length = 50)
    @Schema(description = "Имя клиента", example = "Сидор")
    private String name;

    @Column(name = "client_patronymic", length = 50)
    @Schema(description = "Отчество клиента", example = "Сидорович")
    private String patronymic;

    @Column(name = "client_phone", nullable = false, length = 25)
    @Schema(description = "Номер телефона клиента", example = "+79001234567")
    private String phone;

    @Column(name = "client_email", nullable = false, length = 100, unique = true)
    @Schema(description = "Email клиента", example = "sidorov@example.com")
    private String email;

    @Column(name = "client_password", nullable = false, length = 255)
    @Schema(description = "Пароль клиента")
    private String password;
}