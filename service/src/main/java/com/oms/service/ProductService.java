
package com.oms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oms.repository.OrderLineRepository;
import com.oms.repository.ProductRepository;
import com.oms.repository.InventoryRepository;
import com.oms.model.Product;
import com.oms.model.Inventory;
import com.oms.model.OrderLine;
import com.oms.model.LineCharges;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    Logger logger;

    // Removed ShippingService declaration
    // ShippingService shippingService;

    // Removed getShippingCosts method
    // public Shipping getShippingCosts(String pid) {
    //     Shipping mockShipping = new Shipping();
    //     mockShipping.setStandardShipping(0.0);
    //     mockShipping.setExpressShipping(0.0);
    //     return mockShipping;
    // }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    // Removed getShippingService method
    // public ShippingService getShippingService() {
    //     return shippingService;
    // }

    // Removed setShippingService method
    // public void setShippingService(ShippingService shippingService) {
    //     this.shippingService = shippingService;
    // }

    public Product getProductById(String pid) {
        logger.log(this.getClass().getName());
        Optional<Product> findById = productRepository.findById(pid);
        return findById.orElse(null);
    }

    public Inventory getProductInventory(String pid) {
        logger.log(this.getClass().getName());
        return inventoryService.fetchInventory(pid);
    }

    public List<Inventory> getInventoriesDescribedWith(String text) {
        logger.log(this.getClass().getName());
        List<Inventory> inventoryList = new LinkedList<>();
        List<Product> products = getProductsDescribedWith(text);
        for (Product p : products) {
            inventoryList.add(getProductInventory(p.getProductId()));
        }
        return inventoryList;
    }

    public List<OrderLine> getOrderLinesForProduct(String pid) {
        logger.log(this.getClass().getName());
        return orderLineRepository.findByCustomerSKU(pid);
    }

    public Double getTotalChargesForProduct(String pid) {
        logger.log(this.getClass().getName());
        double res = 0;
        List<OrderLine> orderLines = getOrderLinesForProduct(pid);
        for (OrderLine orderLine : orderLines) {
            LineCharges charges = orderLine.getCharges();
            res += charges.getTotalCharges();
        }
        return res;
    }

    public List<Product> getAllProducts() {
        logger.log(this.getClass().getName());
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        logger.log(this.getClass().getName());
        Optional<Product> findByName = productRepository.findByName(name);
        return findByName.orElse(null);
    }

    public List<Product> getProductsDescribedWith(String text) {
        logger.log(this.getClass().getName());
        return productRepository.findByDescriptionContainingIgnoreCase(text);
    }

    public Product registerProduct(Product product) {
        logger.log(this.getClass().getName());
        Product response = productRepository.save(product);
        return response;
    }
}