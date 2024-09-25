
package com.oms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oms.repository.OrderLineRepository;
import com.oms.repository.ProductRepository;
import com.oms.repository.InventoryRepository;
import com.oms.entity.Product;
import com.oms.entity.Inventory;
import com.oms.entity.OrderLine;
import com.oms.entity.LineCharges;
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

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Product getProductById(String pid) {
        logger.info(this.getClass().getName());
        Optional<Product> findById = productRepository.findById(pid);
        return findById.orElse(null);
    }

    public Inventory getProductInventory(String pid) {
        logger.info(this.getClass().getName());
        return inventoryService.fetchInventory(pid);
    }

    public List<Inventory> getInventoriesDescribedWith(String text) {
        logger.info(this.getClass().getName());
        List<Inventory> inventoryList = new LinkedList<>();
        List<Product> products = getProductsDescribedWith(text);
        for (Product p : products) {
            inventoryList.add(getProductInventory(p.getProductId()));
        }
        return inventoryList;
    }

    public List<OrderLine> getOrderLinesForProduct(String pid) {
        logger.info(this.getClass().getName());
        return orderLineRepository.findByCustomerSKU(pid);
    }

    public Double getTotalChargesForProduct(String pid) {
        logger.info(this.getClass().getName());
        double res = 0;
        List<OrderLine> orderLines = getOrderLinesForProduct(pid);
        for (OrderLine orderLine : orderLines) {
            LineCharges charges = orderLine.getCharges();
            res += charges.getTotalCharges();
        }
        return res;
    }

    public List<Product> getAllProducts() {
        logger.info(this.getClass().getName());
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        logger.info(this.getClass().getName());
        Optional<Product> findByName = productRepository.findByName(name);
        return findByName.orElse(null);
    }

    public List<Product> getProductsDescribedWith(String text) {
        logger.info(this.getClass().getName());
        return productRepository.findByDescriptionContainingIgnoreCase(text);
    }

    public Product registerProduct(Product product) {
        logger.info(this.getClass().getName());
        Product response = productRepository.save(product);
        return response;
    }
}