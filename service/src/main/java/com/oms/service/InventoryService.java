
package com.oms.service;

import com.oms.entity.Inventory;
import com.oms.util.Logger;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class InventoryService {

    @Autowired
    @Qualifier("inventoryRegion")
    Region<String, Inventory> inventoryRegion;

    @Autowired
    Logger logger;

    public Inventory fetchInventory(String skuId) {
        logger.log(this.getClass().getName());
        return inventoryRegion.get(skuId);
    }

    /**
     * Retrieves all inventory records for a given store ID
     * @param storeId The store ID to search for
     * @return List of Inventory objects for the store
     */
    @Transactional
    public List<Inventory> getInventoryByStoreId(String storeId) {
        logger.log(this.getClass().getName());
        String queryString = "SELECT * FROM /inventory i WHERE i.storeId = $1";
        Object[] params = new Object[]{storeId};
        return inventoryRegion.query(queryString, params)
            .asList()
            .stream()
            .map(result -> (Inventory)result)
            .collect(Collectors.toList());
    }

    public Inventory createInventory(Inventory inventory) {
        logger.log(this.getClass().getName());
        inventoryRegion.put(inventory.getSkuId(), inventory);
        return fetchInventory(inventory.getSkuId());
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}