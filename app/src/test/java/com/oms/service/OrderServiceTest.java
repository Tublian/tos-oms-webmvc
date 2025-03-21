
package com.oms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.entity.SalesOrder;
import com.oms.repository.SalesOrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    SalesOrderRepository orderRepository;

    // Use a subclass of OrderService which provides the setter so that the tests compile.
    OrderService orderService = new TestableOrderService();

    @Before
    public void setUp() {
        orderService.setOrderRepository(orderRepository);
    }

    @Test
    public void testGetOrder() {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomerOrderId("1234");
        salesOrder.setOrderStatus("COMPLETED");

        Mockito.when(orderRepository.findById("1234")).thenReturn(java.util.Optional.of(salesOrder));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(salesOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        SalesOrder order = orderService.fetchOrder("1234");

        Assert.assertNotNull(order);
        Assert.assertEquals(order.getCustomerOrderId(), "1234");
        Assert.assertEquals(order.getOrderStatus(), "COMPLETED");
    }

    @Test
    public void testCreateOrder() {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomerOrderId("1234");
        salesOrder.setOrderStatus("COMPLETED");

        Mockito.when(orderRepository.save(salesOrder)).thenReturn(salesOrder);

        SalesOrder order = orderService.saveOrder(salesOrder);

        Assert.assertNotNull(order);
        Assert.assertEquals(order.getCustomerOrderId(), "1234");
        Assert.assertEquals(order.getOrderStatus(), "COMPLETED");
    }

    // Added an inner class to provide the required setter method.
    private static class TestableOrderService extends OrderService {
        public void setOrderRepository(SalesOrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }
    }
}