package com.rentancy.inventorygraphql.api;

import lombok.Data;

@Data
public class Property {
    private final long id;
    private final String streetAddress;
    private final String city;
    private final String country;
    private final String postcode;
}
