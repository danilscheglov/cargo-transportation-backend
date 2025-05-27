package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.CargoDto;
import com.danilscheglov.transportation.service.CargoService;
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
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
@Tag(name = "Cargos", description = "API для работы с грузами")
public class CargoController {
    private final CargoService cargoService;

    @Operation(summary = "Получить все грузы", description = "Возвращает список всех грузов в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список грузов успешно получен", content = @Content(schema = @Schema(implementation = CargoDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<CargoDto>> getAllCargos() {
        return ResponseEntity.ok(cargoService.getAllCargos());
    }

    @Operation(summary = "Получить груз по ID", description = "Возвращает груз по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Груз успешно найден", content = @Content(schema = @Schema(implementation = CargoDto.class))),
            @ApiResponse(responseCode = "404", description = "Груз не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> getCargoById(
            @Parameter(description = "Идентификатор груза", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(cargoService.getCargoById(id));
    }

    @Operation(summary = "Создать новый груз", description = "Создает новый груз в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Груз успешно создан", content = @Content(schema = @Schema(implementation = CargoDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные груза", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CargoDto> createCargo(
            @Parameter(description = "Данные нового груза") @Valid @RequestBody CargoDto cargoDto) {
        return new ResponseEntity<>(cargoService.createCargo(cargoDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить существующий груз", description = "Обновляет данные существующего груза по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Груз успешно обновлен", content = @Content(schema = @Schema(implementation = CargoDto.class))),
            @ApiResponse(responseCode = "404", description = "Груз не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные груза", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> updateCargo(
            @Parameter(description = "Идентификатор груза", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные груза") @Valid @RequestBody CargoDto cargoDto) {
        return ResponseEntity.ok(cargoService.updateCargo(id, cargoDto));
    }

    @Operation(summary = "Удалить груз", description = "Удаляет груз по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Груз успешно удален"),
            @ApiResponse(responseCode = "404", description = "Груз не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(
            @Parameter(description = "Идентификатор груза", example = "1") @PathVariable Long id) {
        cargoService.deleteCargo(id);
        return ResponseEntity.noContent().build();
    }
}