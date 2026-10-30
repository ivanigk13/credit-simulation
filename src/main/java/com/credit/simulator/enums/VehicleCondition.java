package com.credit.simulator.enums;

public enum VehicleCondition {
    BEKAS("Bekas"),
    BARU("Baru");

    private final String name;

    VehicleCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VehicleCondition from(String value) {

        for (VehicleCondition condition : values()) {
            if (condition.name().equalsIgnoreCase(value)) {
                return condition;
            }
        }

        throw new IllegalArgumentException("Invalid vehicle condition");
    }
}
