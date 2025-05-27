package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.FlightDTO;
import com.danilscheglov.transportation.entity.Flight;
import com.danilscheglov.transportation.service.FlightService;
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
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "API для управления рейсами")
public class FlightController {

    private final FlightService flightService;

    @Operation(summary = "Получить список всех рейсов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список рейсов получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class)))
    })
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @Operation(summary = "Получить рейс по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рейс найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Рейс не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(
            @Parameter(description = "ID рейса") @PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @Operation(summary = "Создать новый рейс")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Рейс создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Flight> createFlight(
            @Parameter(description = "Данные рейса") @Valid @RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(flightService.createFlight(flightDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные рейса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные рейса обновлены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Рейс не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(
            @Parameter(description = "ID рейса") @PathVariable Long id,
            @Parameter(description = "Обновленные данные рейса") @Valid @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightDTO));
    }

    @Operation(summary = "Удалить рейс")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Рейс удален"),
            @ApiResponse(responseCode = "404", description = "Рейс не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @Parameter(description = "ID рейса") @PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}