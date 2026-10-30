package com.credit.simulator.enums;

public enum VehicleType {
    MOBIL("Mobil"),
    MOTOR("Motor");

    private final String name;

    VehicleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VehicleType from(String value) {

        for (VehicleType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid vehicle type");
    }
}
