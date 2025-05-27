package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.DriverDTO;
import com.danilscheglov.transportation.entity.Driver;
import com.danilscheglov.transportation.service.DriverService;
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
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(name = "Drivers", description = "API для управления водителями")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Получить список всех водителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список водителей получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)))
    })
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @Operation(summary = "Получить водителя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Водитель найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class))),
            @ApiResponse(responseCode = "404", description = "Водитель не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(
            @Parameter(description = "ID водителя") @PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @Operation(summary = "Создать нового водителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Водитель создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Driver> createDriver(
            @Parameter(description = "Данные водителя") @Valid @RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(driverService.createDriver(driverDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные водителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные водителя обновлены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class))),
            @ApiResponse(responseCode = "404", description = "Водитель не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(
            @Parameter(description = "ID водителя") @PathVariable Long id,
            @Parameter(description = "Обновленные данные водителя") @Valid @RequestBody DriverDTO driverDTO) {
        return ResponseEntity.ok(driverService.updateDriver(id, driverDTO));
    }

    @Operation(summary = "Удалить водителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Водитель удален"),
            @ApiResponse(responseCode = "404", description = "Водитель не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(
            @Parameter(description = "ID водителя") @PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}