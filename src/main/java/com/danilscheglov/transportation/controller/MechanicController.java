package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.service.MechanicService;
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

@PreAuthorize("hasRole('MECHANIC')")
@Tag(name = "Mechanics", description = "API для работы с механиками")
@RestController
@RequestMapping("/api/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    private final MechanicService mechanicService;

    @Operation(summary = "Получить список всех механиков", description = "Возвращает список всех механиков в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список механиков успешно получен", content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(mechanicService.getAllUsers());
    }

    @Operation(summary = "Получить механика по ID", description = "Возвращает информацию о механике по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Механик успешно найден", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Механик не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "Идентификатор механика", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(mechanicService.getUserById(id));
    }
}
