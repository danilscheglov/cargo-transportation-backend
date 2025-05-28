package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.service.DispatcherService;
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

@PreAuthorize("hasRole('DISPATCHER')")
@RestController
@RequestMapping("/api/dispatchers")
@RequiredArgsConstructor
@Tag(name = "Dispatchers", description = "API для управления диспетчерами")
public class DispatcherController {

    private final DispatcherService dispatcherService;

    @Operation(summary = "Получить список всех диспетчеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список диспетчеров получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllDispatchers() {
        return ResponseEntity.ok(dispatcherService.getAllDispatchers());
    }

    @Operation(summary = "Получить диспетчера по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Диспетчер найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Диспетчер не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getDispatcherById(
            @Parameter(description = "ID диспетчера") @PathVariable Long id) {
        return ResponseEntity.ok(dispatcherService.getDispatcherByIdDto(id));
    }
}
