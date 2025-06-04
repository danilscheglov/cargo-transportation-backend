package com.danilscheglov.transportation.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaintenanceStatus {

    WAITING("В ожидании"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена");

    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }

    public static MaintenanceStatus fromDisplayName(String displayName) {
        for (MaintenanceStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + displayName);
    }

}
