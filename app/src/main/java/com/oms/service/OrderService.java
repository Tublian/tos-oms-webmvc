
package com.oms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oms.repository.SalesOrderRepository;
import com.oms.entity.SalesOrder;

@Service
public class OrderService {

    @Autowired
    protected SalesOrderRepository orderRepository;

    public SalesOrder fetchOrder(String customerOrderId) {
        return orderRepository.findById(customerOrderId).orElse(null);
    }

    public SalesOrder saveOrder(SalesOrder salesOrder) {
        return orderRepository.save(salesOrder);
    }
    
    void setOrderRepository(SalesOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}