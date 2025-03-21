package com.oms.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.oms.service.ProductService;
import com.oms.service.ShippingService;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ControllerIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testProductServiceBeanPresent() {
        ProductService productService = applicationContext.getBean(ProductService.class);
        assertNotNull(productService);
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testShippingServiceBeanExcluded() {
        // Attempt to retrieve ShippingService should result in NoSuchBeanDefinitionException
        applicationContext.getBean(ShippingService.class);
    }
}