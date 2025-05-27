package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.OperatorDTO;
import com.danilscheglov.transportation.service.OperatorService;
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

@Tag(name = "Операторы", description = "API для управления операторами в системе")
@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorService operatorService;

    @Operation(summary = "Получить список всех операторов", description = "Возвращает список всех операторов в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список операторов успешно получен", content = @Content(schema = @Schema(implementation = OperatorDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OperatorDTO>> getAllOperators() {
        return ResponseEntity.ok(operatorService.getAllOperators());
    }

    @Operation(summary = "Получить оператора по ID", description = "Возвращает информацию об операторе по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оператор успешно найден", content = @Content(schema = @Schema(implementation = OperatorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Оператор не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperatorDTO> getOperatorById(
            @Parameter(description = "Идентификатор оператора", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(operatorService.getOperatorById(id));
    }

    @Operation(summary = "Создать нового оператора", description = "Создает нового оператора в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Оператор успешно создан", content = @Content(schema = @Schema(implementation = OperatorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные оператора", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OperatorDTO> createOperator(
            @Parameter(description = "Данные нового оператора", required = true) @Valid @RequestBody OperatorDTO operatorDTO) {
        return new ResponseEntity<>(operatorService.createOperator(operatorDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные оператора", description = "Обновляет информацию о существующем операторе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные оператора успешно обновлены", content = @Content(schema = @Schema(implementation = OperatorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Оператор не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные оператора", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OperatorDTO> updateOperator(
            @Parameter(description = "Идентификатор оператора", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные оператора", required = true) @Valid @RequestBody OperatorDTO operatorDTO) {
        return ResponseEntity.ok(operatorService.updateOperator(id, operatorDTO));
    }

    @Operation(summary = "Удалить оператора", description = "Удаляет оператора из системы")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Оператор успешно удален"),
            @ApiResponse(responseCode = "404", description = "Оператор не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(
            @Parameter(description = "Идентификатор оператора", example = "1") @PathVariable Long id) {
        operatorService.deleteOperator(id);
        return ResponseEntity.noContent().build();
    }
}