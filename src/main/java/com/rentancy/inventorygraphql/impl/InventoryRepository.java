package com.rentancy.inventorygraphql.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentancy.inventorygraphql.api.Agreement.Inventory;
import com.rentancy.inventorygraphql.api.Agreement;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class InventoryRepository {
    private static final String AGREEMENTS = "/data/agreements.json";

    private final ObjectMapper objectMapper;
    private final Set<Agreement> agreements;

    InventoryRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.agreements = new HashSet<>();
    }

    Set<Inventory> getInventories() {
        try {
            return findAgreements()
                    .stream()
                    .map(Agreement::getInventory)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get inventories", e);
        }
    }

    Inventory getInventory(long agreementId) {
        try {
            var agreements = findAgreements();
            return agreements
                    .stream()
                    .filter(e -> e.getId() == agreementId)
                    .findAny()
                    .map(Agreement::getInventory)
                    .orElseThrow();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get inventories", e);
        }
    }

    private Set<Agreement> findAgreements() throws IOException {
        return agreements.isEmpty() ? loadAgreements() : agreements;
    }

    private Set<Agreement> loadAgreements() throws IOException {
        var data = new String(new ClassPathResource(AGREEMENTS).getInputStream().readAllBytes());

        agreements.addAll(objectMapper.readValue(data, new TypeReference<>() {}));

        return agreements;
    }
}
