package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Сущность автомобиля")
@Entity
@Table(name = "car")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    @Schema(description = "Идентификатор автомобиля", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "Водитель автомобиля")
    private User driver;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "car_number", nullable = false, length = 20)
    @Schema(description = "Номер автомобиля", example = "А123БВ777")
    private String number;

    @Column(name = "car_model", nullable = false, length = 50)
    @Schema(description = "Модель автомобиля", example = "Camry")
    private String model;

    @Column(name = "car_brand", nullable = false, length = 50)
    @Schema(description = "Марка автомобиля", example = "Toyota")
    private String brand;

    @Column(name = "car_capacity", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Грузоподъемность автомобиля в тоннах", example = "5.5")
    private BigDecimal capacity;

    @Column(name = "car_mileage", nullable = false)
    @Schema(description = "Пробег автомобиля в километрах", example = "150000")
    private Integer mileage;

    @Column(name = "car_condition", nullable = false, length = 20)
    @Schema(description = "Состояние автомобиля", example = "Исправен")
    private String condition;

    @Column(name = "car_last_maintenance_date")
    @Schema(description = "Дата последнего технического обслуживания", example = "2024-03-15")
    private LocalDate lastMaintenanceDate;
}
