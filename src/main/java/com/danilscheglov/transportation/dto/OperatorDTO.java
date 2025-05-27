package com.danilscheglov.transportation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO для оператора")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO {
    @Schema(description = "Идентификатор оператора", example = "1")
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    @Schema(description = "Фамилия оператора", example = "Иванов")
    private String surname;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    @Schema(description = "Имя оператора", example = "Иван")
    private String name;

    @Size(max = 50, message = "Отчество не должно превышать 50 символов")
    @Schema(description = "Отчество оператора", example = "Иванович")
    private String patronymic;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = "^\\+?[0-9]{10,25}$", message = "Неверный формат номера телефона")
    @Size(max = 25, message = "Номер телефона не должен превышать 25 символов")
    @Schema(description = "Номер телефона оператора", example = "+79001234567")
    private String phone;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    @Size(max = 100, message = "Email не должен превышать 100 символов")
    @Schema(description = "Email оператора", example = "ivan@example.com")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
    @Schema(description = "Пароль оператора")
    private String password;
}