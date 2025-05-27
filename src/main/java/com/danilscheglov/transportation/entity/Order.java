package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "Сущность заказа")
@Entity
@Table(name = "\"order\"")
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
    @JoinColumn(name = "client_id", nullable = false)
    @Schema(description = "Клиент, создавший заказ")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    @Schema(description = "Оператор, обрабатывающий заказ")
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    @Schema(description = "Рейс, назначенный на заказ")
    private Flight flight;

    @Column(name = "order_startpoint", nullable = false, length = 255)
    @Schema(description = "Пункт отправления", example = "Москва, ул. Ленина 1")
    private String startpoint;

    @Column(name = "order_endpoint", nullable = false, length = 255)
    @Schema(description = "Пункт назначения", example = "Санкт-Петербург, ул. Невская 10")
    private String endpoint;

    @Column(name = "order_dispatch_date", nullable = false)
    @Schema(description = "Дата отправки", example = "2024-03-15")
    private LocalDate dispatchDate;

    @Column(name = "order_delivery_date", nullable = false)
    @Schema(description = "Дата доставки", example = "2024-03-16")
    private LocalDate deliveryDate;

    @Column(name = "order_status", nullable = false, length = 20)
    @Schema(description = "Статус заказа", example = "В обработке")
    private String status;
}