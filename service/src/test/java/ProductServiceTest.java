package com.oms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.oms.entity.Product;
import com.oms.repository.ProductRepository;
import com.oms.util.Logger;

public class ProductServiceTest {

    private ProductService productService;
    private InMemoryProductRepository inMemoryRepo;
    private DummyLogger dummyLogger;
    
    @BeforeEach
    public void setup() {
        // initialize the in-memory repository and dummy logger and set them in productService
        inMemoryRepo = new InMemoryProductRepository();
        dummyLogger = new DummyLogger();
        
        productService = new ProductService();
        productService.setProductRepository(inMemoryRepo);
        productService.setLogger(dummyLogger);
    }
    
    // Test: 0
    // Input: Invoke registerProduct() with a valid Product object and then retrieve it using getProductById() or getProductByName().
    // Expected Output: The returned Product should match the input.
    @Test
    public void testRegisterAndRetrieveProduct() {
        // create a product instance with sample details
        Product product = new Product();
        product.setProductId("p1");
        product.setName("TestProduct");
        product.setDescription("A product for testing");
        
        // register the product
        Product registeredProduct = productService.registerProduct(product);
        assertNotNull(registeredProduct, "Registered product should not be null");
        assertEquals("p1", registeredProduct.getProductId());
        assertEquals("TestProduct", registeredProduct.getName());
        assertEquals("A product for testing", registeredProduct.getDescription());
        
        // retrieve product by Id
        Product fetchedById = productService.getProductById("p1");
        assertNotNull(fetchedById, "Fetched product by Id should not be null");
        assertEquals("p1", fetchedById.getProductId());
        assertEquals("TestProduct", fetchedById.getName());
        assertEquals("A product for testing", fetchedById.getDescription());
        
        // retrieve product by Name
        Product fetchedByName = productService.getProductByName("TestProduct");
        assertNotNull(fetchedByName, "Fetched product by Name should not be null");
        assertEquals("p1", fetchedByName.getProductId());
        assertEquals("TestProduct", fetchedByName.getName());
        assertEquals("A product for testing", fetchedByName.getDescription());
    }
    
    // Test: 1
    // Input: Call getAllProducts() after adding several products.
    // Expected Output: A list containing all added products is returned.
    @Test
    public void testGetAllProducts() {
        // register multiple product entries
        Product product1 = new Product();
        product1.setProductId("p1");
        product1.setName("Product1");
        product1.setDescription("Description1");
        
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setName("Product2");
        product2.setDescription("Description2");
        
        Product product3 = new Product();
        product3.setProductId("p3");
        product3.setName("Product3");
        product3.setDescription("Description3");
        
        productService.registerProduct(product1);
        productService.registerProduct(product2);
        productService.registerProduct(product3);
        
        // retrieve all products and assert the list size
        List<Product> allProducts = productService.getAllProducts();
        assertNotNull(allProducts, "The list of products should not be null");
        assertEquals(3, allProducts.size(), "There should be 3 products registered");
    }
    
    // In-memory implementation of ProductRepository for testing purposes.
    static class InMemoryProductRepository implements ProductRepository {
        private Map<String, Product> store = new HashMap<>();
    
        @Override
        public Product save(Product product) {
            store.put(product.getProductId(), product);
            return product;
        }
    
        @Override
        public Optional<Product> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }
    
        @Override
        public Optional<Product> findByName(String name) {
            return store.values().stream()
                    .filter(p -> p.getName() != null && p.getName().equals(name))
                    .findFirst();
        }
    
        @Override
        public List<Product> findAll() {
            return new ArrayList<>(store.values());
        }
    
        @Override
        public List<Product> findByDescriptionContainingIgnoreCase(String text) {
            List<Product> result = new ArrayList<>();
            if (text == null) {
                return result;
            }
            String lowerText = text.toLowerCase();
            for (Product p : store.values()) {
                if (p.getDescription() != null && p.getDescription().toLowerCase().contains(lowerText)) {
                    result.add(p);
                }
            }
            return result;
        }
    }
    
    // Dummy logger implementation that does nothing.
    static class DummyLogger implements Logger {
        @Override
        public void log(String message) {
            // Do nothing.
        }
    }
}