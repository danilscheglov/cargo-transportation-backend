package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasRole('DRIVER')")
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(name = "Drivers", description = "API для управления водителями")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Получить список всех водителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список водителей получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @Operation(summary = "Получить водителя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Водитель найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Водитель не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getDriverById(
            @Parameter(description = "ID водителя") @PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverByIdDto(id));
    }
}
