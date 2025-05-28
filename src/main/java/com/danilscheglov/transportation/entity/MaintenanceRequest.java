package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "Сущность заявки на обслуживание")
@Entity
@Table(name = "maintenance_request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_request_id")
    @Schema(description = "Идентификатор заявки", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @Schema(description = "Автомобиль")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Механик")
    private User User;

    @Column(name = "filling_date", nullable = false)
    @Schema(description = "Дата заполнения", example = "2024-03-15")
    private LocalDate fillingDate;

    @Column(name = "service_type", nullable = false, length = 50)
    @Schema(description = "Тип обслуживания", example = "Плановое ТО")
    private String serviceType;

    @Column(name = "maintenance_request_status", nullable = false, length = 20)
    @Schema(description = "Статус заявки", example = "В обработке")
    private String status;

    @Column(name = "maintenance_request_note")
    @Schema(description = "Примечание к заявке", example = "Требуется замена масла")
    private String note;
}
