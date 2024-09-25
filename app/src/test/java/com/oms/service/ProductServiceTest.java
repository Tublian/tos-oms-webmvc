package com.oms.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.entity.Product;
import com.oms.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

  @Mock
  ProductRepository productRepository;

  ProductService productService = new ProductService();

  @Before
  public void setUp() {
    productService.productRepository = productRepository;
    productService.setLogger(LoggerFactory.getLogger(ProductService.class));
  }

  @Test
  public void testRegisterProduct() {
    Product p1 = new Product();
    p1.setProductId("1");
    p1.setName("Samsung Note 9");
    p1.setDescription("A nice big phone");
    p1.setManuf("Samsung");

    when(productRepository.save(p1)).thenReturn(p1);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      System.out.println(objectMapper.writeValueAsString(p1));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    assertNotNull(productService.registerProduct(p1));
  }

  @Test
  public void testGetProductById() {
    String productId = "123";
    Product product = new Product();
    product.setProductId(productId);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    Product result = productService.getProductById(productId);

    assertEquals(productId, result.getProductId());
  }
}