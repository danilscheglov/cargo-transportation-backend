package com.danilscheglov.transportation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Сущность груза")
@Entity
@Table(name = "cargo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_id")
    @Schema(description = "Идентификатор груза", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Schema(description = "Заказ, к которому относится груз")
    private Order order;

    @Column(name = "cargo_type", nullable = false, length = 50)
    @Schema(description = "Тип груза", example = "Бытовая техника")
    private String type;

    @Column(name = "cargo_weight", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Вес груза в килограммах", example = "150.50")
    private BigDecimal weight;

    @Column(name = "cargo_volume", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Объем груза в кубических метрах", example = "2.5")
    private BigDecimal volume;
}