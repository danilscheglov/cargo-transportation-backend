package com.danilscheglov.transportation.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarCondition {
    OPERATIONAL("Исправно"),
    IN_SERVICE("На обслуживании"),
    UNDER_REPAIR("На ремонте");

    private final String displayName;

    public static CarCondition fromDisplayName(String displayName) {
        for (CarCondition condition : values()) {
            if (condition.displayName.equalsIgnoreCase(displayName)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Unknown car condition: " + displayName);
    }
}
