package com.rentancy.inventorygraphql.impl;

import com.rentancy.inventorygraphql.api.Agreement.Inventory;
import com.rentancy.inventorygraphql.api.InventoryService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Inventory> getInventories() {
        return repository.getInventories();
    }

    @Override
    public Inventory getInventory(long id) {
        return repository.getInventory(id);
    }
}
