package com.rentancy.inventorygraphql.api;

import java.util.Set;
import com.rentancy.inventorygraphql.api.Agreement.Inventory;

public interface InventoryService {

    Set<Inventory> getInventories();
    Inventory getInventory(long agreementId);
}
