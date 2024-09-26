package com.oms.controller;

import com.oms.entity.Product;
import com.oms.service.ProductService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Logger logger;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testGetProductById() {
        Product product = new Product();
        Mockito.when(productService.getProductById("123")).thenReturn(product);

        Product result = productController.getProductById("123");

        Mockito.verify(logger).log(ProductController.class.getName());
        Assert.assertEquals(product, result);
    }

    @Test
    public void testRegisterNewProduct() {
        Product product = new Product();
        Mockito.when(productService.registerProduct(product)).thenReturn(product);

        Product result = productController.registerNewProduct(product);

        Mockito.verify(logger).log(ProductController.class.getName());
        Assert.assertEquals(product, result);
    }
}