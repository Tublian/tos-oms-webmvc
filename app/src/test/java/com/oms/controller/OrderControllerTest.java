package com.oms.controller;

import com.oms.entity.SalesOrder;
import com.oms.service.OrderService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private Logger logger;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testFetchSalesOrder() {
        SalesOrder salesOrder = new SalesOrder();
        Mockito.when(orderService.fetchOrder("123")).thenReturn(salesOrder);

        SalesOrder result = orderController.fetchSalesOrder("123");

        Mockito.verify(logger).log(OrderController.class.getName());
        Assert.assertEquals(salesOrder, result);
    }

    @Test
    public void testCreateSalesOrder() {
        SalesOrder salesOrder = new SalesOrder();
        Mockito.when(orderService.saveOrder(salesOrder)).thenReturn(salesOrder);

        SalesOrder result = orderController.createSalesOrder(salesOrder);

        Mockito.verify(logger).log(OrderController.class.getName());
        Assert.assertEquals(salesOrder, result);
    }
}