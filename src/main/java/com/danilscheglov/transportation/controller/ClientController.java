package com.danilscheglov.transportation.controller;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.User;
import com.danilscheglov.transportation.service.ClientService;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "API для управления клиентами")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Получить список всех клиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список клиентов получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Operation(summary = "Получить клиента по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден", content = @Content)
    })
    @GetMapping("/{email}")
    public ResponseEntity<User> getClientById(
            @Parameter(description = "ID клиента") @PathVariable String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }

    @Operation(summary = "Создать нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Клиент создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<User> createClient(
            @Parameter(description = "Данные клиента") @Valid @RequestBody UserDto clientDTO) {
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить данные клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные клиента обновлены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{email}")
    public ResponseEntity<User> updateClient(
            @Parameter(description = "ID клиента") @PathVariable String email,
            @Parameter(description = "Обновленные данные клиента") @Valid @RequestBody UserDto clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(email, clientDTO));
    }

    @Operation(summary = "Удалить клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Клиент удален"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "ID клиента") @PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
