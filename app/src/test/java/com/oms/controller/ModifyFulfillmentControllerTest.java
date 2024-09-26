package com.oms.controller;

import com.oms.entity.SalesOrder;
import com.oms.service.ModifyFulfillmentService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModifyFulfillmentControllerTest {

    @Mock
    private ModifyFulfillmentService modifyFulfillmentService;

    @Mock
    private Logger logger;

    @InjectMocks
    private ModifyFulfillmentController modifyFulfillmentController;

    @Test
    public void testModifyStorePickupToShipping() {
        SalesOrder salesOrder = new SalesOrder();
        Mockito.when(modifyFulfillmentService.modifyToShipping("123", salesOrder)).thenReturn(salesOrder);

        SalesOrder result = modifyFulfillmentController.modifyStorePickupToShipping("123", salesOrder);

        Mockito.verify(logger).log(ModifyFulfillmentController.class.getName());
        Assert.assertEquals(salesOrder, result);
    }

    @Test
    public void testModifyShippingToStorePickUp() {
        SalesOrder salesOrder = new SalesOrder();
        Mockito.when(modifyFulfillmentService.modifyToStorePickup("123", salesOrder)).thenReturn(salesOrder);

        SalesOrder result = modifyFulfillmentController.modifyShippingToStorePickUp("123", salesOrder);

        Mockito.verify(logger).log(ModifyFulfillmentController.class.getName());
        Assert.assertEquals(salesOrder, result);
    }
}