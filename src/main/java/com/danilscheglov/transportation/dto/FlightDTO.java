package com.danilscheglov.transportation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long id;

    @NotNull(message = "ID водителя обязателен")
    private Long driverId;

    @NotNull(message = "ID диспетчера обязателен")
    private Long dispatcherId;

    @NotNull(message = "ID машины обязателен")
    private Long carId;

    @NotBlank(message = "Пункт отправления обязателен")
    @Size(max = 255, message = "Пункт отправления не должен превышать 255 символов")
    private String startPoint;

    @NotBlank(message = "Пункт назначения обязателен")
    @Size(max = 255, message = "Пункт назначения не должен превышать 255 символов")
    private String endPoint;

    @NotNull(message = "Расстояние обязательно")
    @DecimalMin(value = "0.1", message = "Расстояние должно быть больше 0")
    @DecimalMax(value = "99999.99", message = "Расстояние не должно превышать 99999.99")
    private BigDecimal distance;

    @NotNull(message = "Дата отправления обязательна")
    @FutureOrPresent(message = "Дата отправления должна быть в настоящем или будущем")
    private LocalDate departureDate;
}