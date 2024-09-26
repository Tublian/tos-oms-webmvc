package com.oms.controller;

import com.oms.entity.Shipping;
import com.oms.service.ShippingService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShippingPriceControllerTest {

    @Mock
    private ShippingService shippingService;

    @Mock
    private Logger logger;

    @InjectMocks
    private ShippingPriceController shippingPriceController;

    @Test
    public void testFetchSalesOrder() {
        Shipping shipping = new Shipping();
        Mockito.when(shippingService.fetchShippingCharges("123")).thenReturn(shipping);

        Shipping result = shippingPriceController.fetchSalesOrder("123");

        Mockito.verify(logger).log(ShippingPriceController.class.getName());
        Assert.assertEquals(shipping, result);
    }

    @Test
    public void testCreateShipping() {
        Shipping shipping = new Shipping();
        Mockito.when(shippingService.createShipping(shipping)).thenReturn(shipping);

        Shipping result = shippingPriceController.createShipping(shipping);

        Mockito.verify(logger).log(ShippingPriceController.class.getName());
        Assert.assertEquals(shipping, result);
    }
}