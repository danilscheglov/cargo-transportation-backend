package com.danilscheglov.transportation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO заказа")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @Schema(description = "Идентификатор заказа", example = "1")
    private Long id;

    @NotNull(message = "Идентификатор клиента не может быть пустым")
    @Schema(description = "Идентификатор клиента", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clientId;

    @NotNull(message = "Идентификатор оператора не может быть пустым")
    @Schema(description = "Идентификатор оператора", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long operatorId;

    @Schema(description = "Идентификатор рейса", example = "1")
    private Long flightId;

    @NotBlank(message = "Пункт отправления не может быть пустым")
    @Schema(description = "Пункт отправления", example = "Москва, ул. Ленина 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startpoint;

    @NotBlank(message = "Пункт назначения не может быть пустым")
    @Schema(description = "Пункт назначения", example = "Санкт-Петербург, ул. Невская 10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endpoint;

    @NotNull(message = "Дата отправки не может быть пустой")
    @Future(message = "Дата отправки должна быть в будущем")
    @Schema(description = "Дата отправки", example = "2024-03-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dispatchDate;

    @NotNull(message = "Дата доставки не может быть пустой")
    @Future(message = "Дата доставки должна быть в будущем")
    @Schema(description = "Дата доставки", example = "2024-03-16", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate deliveryDate;

    @NotBlank(message = "Статус заказа не может быть пустым")
    @Pattern(regexp = "^(Создан|В обработке|Подтвержден|В пути|Доставлен|Отменен)$", message = "Недопустимый статус заказа")
    @Schema(description = "Статус заказа", example = "В обработке", allowableValues = { "Создан", "В обработке",
            "Подтвержден", "В пути", "Доставлен", "Отменен" }, requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;
}