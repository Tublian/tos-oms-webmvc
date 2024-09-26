package com.oms.controller;

import com.oms.entity.Inventory;
import com.oms.service.InventoryService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @Mock
    private Logger logger;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    public void testFetchInventory() {
        Inventory inventory = new Inventory();
        Mockito.when(inventoryService.fetchInventory("123")).thenReturn(inventory);

        Inventory result = inventoryController.fetchInventory("123");

        Mockito.verify(logger).log(InventoryController.class.getName());
        Assert.assertEquals(inventory, result);
    }

    @Test
    public void testCreateInventory() {
        Inventory inventory = new Inventory();
        Mockito.when(inventoryService.createInventory(inventory)).thenReturn(inventory);

        Inventory result = inventoryController.createInventory(inventory);

        Mockito.verify(logger).log(InventoryController.class.getName());
        Assert.assertEquals(inventory, result);
    }
}