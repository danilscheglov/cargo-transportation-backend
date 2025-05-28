package com.danilscheglov.transportation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO для заявки на обслуживание")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDTO {
    @Schema(description = "Идентификатор заявки", example = "1")
    private Long id;

    @NotNull(message = "ID автомобиля обязателен")
    @Schema(description = "ID автомобиля", example = "1")
    private Long carId;

    @NotNull(message = "ID механика обязателен")
    @Schema(description = "ID механика", example = "1")
    private Long UserId;

    @NotNull(message = "Дата заполнения обязательна")
    @Schema(description = "Дата заполнения", example = "2024-03-15")
    private LocalDate fillingDate;

    @NotBlank(message = "Тип обслуживания обязателен")
    @Size(max = 50, message = "Тип обслуживания не должен превышать 50 символов")
    @Schema(description = "Тип обслуживания", example = "Плановое ТО")
    private String serviceType;

    @NotBlank(message = "Статус заявки обязателен")
    @Size(max = 20, message = "Статус заявки не должен превышать 20 символов")
    @Schema(description = "Статус заявки", example = "В обработке")
    private String status;

    @Size(max = 1000, message = "Примечание не должно превышать 1000 символов")
    @Schema(description = "Примечание к заявке", example = "Требуется замена масла")
    private String note;
}
