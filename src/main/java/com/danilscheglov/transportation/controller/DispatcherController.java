package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.DispatcherDTO;
import com.danilscheglov.transportation.entity.Dispatcher;
import com.danilscheglov.transportation.service.DispatcherService;
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
@RequestMapping("/api/dispatchers")
@RequiredArgsConstructor
@Tag(name = "Dispatchers", description = "API для управления диспетчерами")
public class DispatcherController {

    private final DispatcherService dispatcherService;

    @Operation(summary = "Получить список всех диспетчеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список диспетчеров получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dispatcher.class)))
    })
    @GetMapping
    public ResponseEntity<List<Dispatcher>> getAllDispatchers() {
        return ResponseEntity.ok(dispatcherService.getAllDispatchers());
    }

    @Operation(summary = "Получить диспетчера по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Диспетчер найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dispatcher.class))),
            @ApiResponse(responseCode = "404", description = "Диспетчер не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Dispatcher> getDispatcherById(
            @Parameter(description = "ID диспетчера") @PathVariable Long id) {
        return ResponseEntity.ok(dispatcherService.getDispatcherById(id));
    }

    @Operation(summary = "Создать нового диспетчера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Диспетчер создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dispatcher.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Dispatcher> createDispatcher(
            @Parameter(description = "Данные диспетчера") @Valid @RequestBody DispatcherDTO dispatcherDTO) {
        return new ResponseEntity<>(dispatcherService.createDispatcher(dispatcherDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные диспетчера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные диспетчера обновлены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dispatcher.class))),
            @ApiResponse(responseCode = "404", description = "Диспетчер не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Dispatcher> updateDispatcher(
            @Parameter(description = "ID диспетчера") @PathVariable Long id,
            @Parameter(description = "Обновленные данные диспетчера") @Valid @RequestBody DispatcherDTO dispatcherDTO) {
        return ResponseEntity.ok(dispatcherService.updateDispatcher(id, dispatcherDTO));
    }

    @Operation(summary = "Удалить диспетчера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Диспетчер удален"),
            @ApiResponse(responseCode = "404", description = "Диспетчер не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispatcher(
            @Parameter(description = "ID диспетчера") @PathVariable Long id) {
        dispatcherService.deleteDispatcher(id);
        return ResponseEntity.noContent().build();
    }
}