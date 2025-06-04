package com.danilscheglov.transportation.entity;

import com.danilscheglov.transportation.entity.common.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Сущность заказа")
@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @Schema(description = "Идентификатор заказа", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Клиент, создавший заказ")
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @Schema(description = "Водитель, назначенный на заказ")
    private User driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @Schema(description = "Рейс, назначенный на заказ")
    private Car car;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Column(name = "order_startpoint", nullable = false, length = 255)
    @Schema(description = "Пункт отправления", example = "Москва, ул. Ленина 1")
    private String startpoint;

    @Column(name = "order_endpoint", nullable = false, length = 255)
    @Schema(description = "Пункт назначения", example = "Санкт-Петербург, ул. Невская 10")
    private String endpoint;

    @Column(name = "order_status", nullable = false, length = 20)
    @Schema(description = "Статус заказа", example = "В обработке")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Дата и время создания заказа", example = "2025-05-29T15:30:00")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
