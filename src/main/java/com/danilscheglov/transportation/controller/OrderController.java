package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.OrderDTO;
import com.danilscheglov.transportation.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API для работы с заказами")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Получить все заказы", description = "Возвращает список всех заказов в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Получить заказ по ID", description = "Возвращает заказ по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ успешно найден", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "Идентификатор заказа", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Создать новый заказ", description = "Создает новый заказ в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заказа", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @Parameter(description = "Данные нового заказа") @Valid @RequestBody OrderDTO orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить существующий заказ", description = "Обновляет данные существующего заказа по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ успешно обновлен", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заказа", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @Parameter(description = "Идентификатор заказа", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные заказа") @Valid @RequestBody OrderDTO orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заказ успешно удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "Идентификатор заказа", example = "1") @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}