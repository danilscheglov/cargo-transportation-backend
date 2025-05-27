package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.MechanicDTO;
import com.danilscheglov.transportation.service.MechanicService;
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

@Tag(name = "Mechanics", description = "API для работы с механиками")
@RestController
@RequestMapping("/api/mechanics")
@RequiredArgsConstructor
public class MechanicController {
    private final MechanicService mechanicService;

    @Operation(summary = "Получить список всех механиков", description = "Возвращает список всех механиков в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список механиков успешно получен", content = @Content(schema = @Schema(implementation = MechanicDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MechanicDTO>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }

    @Operation(summary = "Получить механика по ID", description = "Возвращает информацию о механике по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Механик успешно найден", content = @Content(schema = @Schema(implementation = MechanicDTO.class))),
            @ApiResponse(responseCode = "404", description = "Механик не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MechanicDTO> getMechanicById(
            @Parameter(description = "Идентификатор механика", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(mechanicService.getMechanicById(id));
    }

    @Operation(summary = "Создать нового механика", description = "Создает нового механика в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Механик успешно создан", content = @Content(schema = @Schema(implementation = MechanicDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные механика", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MechanicDTO> createMechanic(
            @Parameter(description = "Данные нового механика", required = true) @Valid @RequestBody MechanicDTO mechanicDto) {
        return new ResponseEntity<>(mechanicService.createMechanic(mechanicDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные механика", description = "Обновляет информацию о существующем механике")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные механика успешно обновлены", content = @Content(schema = @Schema(implementation = MechanicDTO.class))),
            @ApiResponse(responseCode = "404", description = "Механик не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные механика", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MechanicDTO> updateMechanic(
            @Parameter(description = "Идентификатор механика", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные механика", required = true) @Valid @RequestBody MechanicDTO mechanicDto) {
        return ResponseEntity.ok(mechanicService.updateMechanic(id, mechanicDto));
    }

    @Operation(summary = "Удалить механика", description = "Удаляет механика из системы")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Механик успешно удален"),
            @ApiResponse(responseCode = "404", description = "Механик не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanic(
            @Parameter(description = "Идентификатор механика", example = "1") @PathVariable Long id) {
        mechanicService.deleteMechanic(id);
        return ResponseEntity.noContent().build();
    }
}