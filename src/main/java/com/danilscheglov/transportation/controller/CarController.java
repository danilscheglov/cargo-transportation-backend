package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.CarDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.service.CarService;
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

@Tag(name = "Cars", description = "API для управления автомобилями")
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Operation(summary = "Получить список всех автомобилей", description = "Возвращает список всех автомобилей в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список автомобилей успешно получен", content = @Content(schema = @Schema(implementation = Car.class)))
    })
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @Operation(summary = "Получить автомобиль по ID", description = "Возвращает информацию об автомобиле по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно найден", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(
            @Parameter(description = "Идентификатор автомобиля", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @Operation(summary = "Создать новый автомобиль", description = "Создает новый автомобиль в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Автомобиль успешно создан", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные автомобиля", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Car> createCar(
            @Parameter(description = "Данные нового автомобиля", required = true) @Valid @RequestBody CarDTO carDto) {
        return new ResponseEntity<>(carService.createCar(carDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные автомобиля", description = "Обновляет информацию о существующем автомобиле")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные автомобиля успешно обновлены", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные автомобиля", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
            @Parameter(description = "Идентификатор автомобиля", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные автомобиля", required = true) @Valid @RequestBody CarDTO carDto) {
        return ResponseEntity.ok(carService.updateCar(id, carDto));
    }

    @Operation(summary = "Удалить автомобиль", description = "Удаляет автомобиль из системы")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Автомобиль успешно удален"),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(
            @Parameter(description = "Идентификатор автомобиля", example = "1") @PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
