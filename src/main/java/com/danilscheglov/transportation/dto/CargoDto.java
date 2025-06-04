package com.danilscheglov.transportation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "DTO груза")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {

    @Schema(description = "Идентификатор груза", example = "1")
    private Long id;

    @NotNull(message = "Идентификатор заказа не может быть пустым")
    @Schema(description = "Идентификатор заказа", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @NotBlank(message = "Тип груза не может быть пустым")
    @Size(min = 3, max = 50, message = "Длина типа груза должна быть от 3 до 50 символов")
    @Schema(description = "Тип груза", example = "Бытовая техника", minLength = 3, maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @NotNull(message = "Вес груза не может быть пустым")
    @Positive(message = "Вес груза должен быть положительным числом")
    @DecimalMax(value = "100000.00", message = "Вес груза не может превышать 100 тонн")
    @Schema(description = "Вес груза в килограммах", example = "150.50", minimum = "0.01", maximum = "100000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal weight;

    @NotNull(message = "Объем груза не может быть пустым")
    @Positive(message = "Объем груза должен быть положительным числом")
    @DecimalMax(value = "1000.00", message = "Объем груза не может превышать 1000 кубических метров")
    @Schema(description = "Объем груза в кубических метрах", example = "2.5", minimum = "0.01", maximum = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal volume;

}
