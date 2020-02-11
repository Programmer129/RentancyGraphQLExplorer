package com.rentancy.inventorygraphql.api;

import lombok.Data;

@Data
public class Tenant {
    private final String email;
    private final String firstName;
    private final String lastName;
}
