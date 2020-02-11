package com.rentancy.inventorygraphql.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Agreement {
    private final long id;
    private final String userId;
    private final long propertyId;
    private final boolean furnished;
    private final LocalDate signedDate;
    private final LocalDate contractStartDate;
    private final LocalDate contractEndDate;
    private final String deposit;
    private final Inventory inventory;

    @Data
    public static class Inventory {
        private final int wallsAndCeilings;
        private final int floorCoverings;
        private final int windows;
        private final int doors;
        private final int lights;
        private final int furniture;
        private final int skirting;
    }
}
