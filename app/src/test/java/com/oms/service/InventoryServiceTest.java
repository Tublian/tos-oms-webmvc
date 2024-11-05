package com.oms.service;

import com.oms.entity.Inventory;
import com.oms.util.Logger;

import org.apache.geode.cache.Region;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

    @Mock
    Region<String, Inventory> inventoryRegion;
    InventoryService inventoryService = new InventoryService();
    Logger logger;

    @Before
    public void setUp() {
        inventoryService.inventoryRegion = inventoryRegion;
        logger = mock(Logger.class);
        inventoryService.setLogger(logger);
    }

    @Test
    public void fetchInventory() {
        Inventory inventory = new Inventory("SKU123", "281", 10);

        when(inventoryRegion.get("SKU123")).thenReturn(inventory);

        Inventory response = inventoryService.fetchInventory("SKU123");

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getSkuId(), "SKU123");
        Assert.assertEquals(response.getQuantity(), 10);
    }

    @Test
    public void createInventory() {
        Inventory inventory = new Inventory("SKU123", "281", 10);

        when(inventoryRegion.get("SKU123")).thenReturn(inventory);

        Inventory response = inventoryService.createInventory(inventory);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getSkuId(), inventory.getSkuId());
        Assert.assertEquals(response.getStoreId(), inventory.getStoreId());
        Assert.assertEquals(response.getQuantity(), inventory.getQuantity());
    }

    @Test
    public void testLoggerDuringFetchInventory() {
        Inventory inventory = new Inventory("SKU123", "281", 10);

        when(inventoryRegion.get("SKU123")).thenReturn(inventory);

        inventoryService.fetchInventory("SKU123");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(logger).log(captor.capture());
        Assert.assertTrue(captor.getValue().contains("InventoryService"));
    }
}