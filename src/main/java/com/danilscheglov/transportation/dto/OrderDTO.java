package com.danilscheglov.transportation.dto;

import com.danilscheglov.transportation.entity.common.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO заказа")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    @Schema(description = "Идентификатор заказа", example = "1")
    private Long id;

    @NotNull(message = "Почта клиента не может быть пустым")
    @Schema(description = "Почта клиента", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    private UserDto client;

    @Schema(description = "Идентификатор машины", example = "1")
    private CarDTO car;

    @Schema(description = "Параметры груза", example = "1")
    private CargoDto cargo;

    @NotBlank(message = "Пункт отправления не может быть пустым")
    @Schema(description = "Пункт отправления", example = "Москва, ул. Ленина 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startpoint;

    @NotBlank(message = "Пункт назначения не может быть пустым")
    @Schema(description = "Пункт назначения", example = "Санкт-Петербург, ул. Невская 10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endpoint;

    @NotNull(message = "Статус заказа не может быть пустым")
    @Schema(description = "Статус заказа", example = "В обработке", allowableValues = {"Создан", "В обработке",
            "Подтвержден", "В пути", "Доставлен"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderStatus status;

    @Schema(description = "Дата создания", example = "1")
    private LocalDate createdAt;
}
