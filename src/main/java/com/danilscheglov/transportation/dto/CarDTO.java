package com.danilscheglov.transportation.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarDTO {
    private Long id;

    private Long driverId;

    private UserDto driver;

    @NotBlank(message = "Номер автомобиля обязателен")
    @Pattern(regexp = "^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$", message = "Неверный формат номера автомобиля. Пример: А123ВС77")
    private String number;

    @NotBlank(message = "Модель автомобиля обязательна")
    private String model;

    @NotBlank(message = "Марка автомобиля обязательна")
    private String brand;

    @NotNull(message = "Грузоподъемность обязательна")
    @DecimalMin(value = "0.0", message = "Грузоподъемность должна быть больше 0")
    private BigDecimal capacity;

    @NotNull(message = "Пробег обязателен")
    @Min(value = 0, message = "Пробег не может быть отрицательным")
    private Integer mileage;

    @NotBlank(message = "Состояние автомобиля обязательно")
    @Pattern(
            regexp = "^(OPERATIONAL|IN_SERVICE|UNDER_REPAIR)$",
            message = "Недопустимое состояние автомобиля"
    )
    private String condition;

    private LocalDate lastMaintenanceDate;
}
