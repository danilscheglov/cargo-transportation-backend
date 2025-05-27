package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.MaintenanceRequestDTO;
import com.danilscheglov.transportation.service.MaintenanceRequestService;
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

@Tag(name = "Maintenance Requests", description = "API для работы с заявками на обслуживание")
@RestController
@RequestMapping("/api/maintenance-requests")
@RequiredArgsConstructor
public class MaintenanceRequestController {
    private final MaintenanceRequestService maintenanceRequestService;

    @Operation(summary = "Получить список всех заявок", description = "Возвращает список всех заявок на техническое обслуживание")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список заявок успешно получен", content = @Content(schema = @Schema(implementation = MaintenanceRequestDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MaintenanceRequestDTO>> getAllMaintenanceRequests() {
        return ResponseEntity.ok(maintenanceRequestService.getAllMaintenanceRequests());
    }

    @Operation(summary = "Получить заявку по ID", description = "Возвращает информацию о заявке по её идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заявка успешно найдена", content = @Content(schema = @Schema(implementation = MaintenanceRequestDTO.class))),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDTO> getMaintenanceRequestById(
            @Parameter(description = "Идентификатор заявки", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRequestService.getMaintenanceRequestById(id));
    }

    @Operation(summary = "Создать новую заявку", description = "Создает новую заявку на техническое обслуживание")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заявка успешно создана", content = @Content(schema = @Schema(implementation = MaintenanceRequestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заявки", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MaintenanceRequestDTO> createMaintenanceRequest(
            @Parameter(description = "Данные новой заявки", required = true) @Valid @RequestBody MaintenanceRequestDTO maintenanceRequestDto) {
        return new ResponseEntity<>(maintenanceRequestService.createMaintenanceRequest(maintenanceRequestDto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные заявки", description = "Обновляет информацию о существующей заявке")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные заявки успешно обновлены", content = @Content(schema = @Schema(implementation = MaintenanceRequestDTO.class))),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заявки", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDTO> updateMaintenanceRequest(
            @Parameter(description = "Идентификатор заявки", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные заявки", required = true) @Valid @RequestBody MaintenanceRequestDTO maintenanceRequestDto) {
        return ResponseEntity.ok(maintenanceRequestService.updateMaintenanceRequest(id, maintenanceRequestDto));
    }

    @Operation(summary = "Удалить заявку", description = "Удаляет заявку из системы")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заявка успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceRequest(
            @Parameter(description = "Идентификатор заявки", example = "1") @PathVariable Long id) {
        maintenanceRequestService.deleteMaintenanceRequest(id);
        return ResponseEntity.noContent().build();
    }
}