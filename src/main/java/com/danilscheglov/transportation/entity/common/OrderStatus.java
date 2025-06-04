package com.danilscheglov.transportation.entity.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    NEW("Новый"),
    ASSIGNED("Назначен"),
    IN_PROGRESS("В процессе"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён");

    private final String displayName;

    @JsonValue
    public String toValue() {
        return displayName;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.displayName.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неизвестный статус: " + value));
    }
}
