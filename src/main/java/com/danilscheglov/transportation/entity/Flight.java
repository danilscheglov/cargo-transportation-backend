package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Сущность рейса")
@Entity
@Table(name = "flight")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    @Schema(description = "Идентификатор рейса", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    @Schema(description = "Водитель рейса")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatcher_id", nullable = false)
    @Schema(description = "Диспетчер рейса")
    private Dispatcher dispatcher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @Schema(description = "Автомобиль рейса")
    private Car car;

    @Column(name = "flight_startpoint", nullable = false, length = 100)
    @Schema(description = "Пункт отправления", example = "Москва")
    private String startPoint;

    @Column(name = "flight_endpoint", nullable = false, length = 100)
    @Schema(description = "Пункт назначения", example = "Санкт-Петербург")
    private String endPoint;

    @Column(name = "flight_distance", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Расстояние в километрах", example = "705.5")
    private BigDecimal distance;

    @Column(name = "flight_departure_date", nullable = false)
    @Schema(description = "Дата отправления", example = "2024-03-15")
    private LocalDate departureDate;
}