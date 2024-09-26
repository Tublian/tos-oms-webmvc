package com.oms.controller;

import com.oms.service.StoreSearchService;
import com.oms.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StoreSearchControllerTest {

    @Mock
    private StoreSearchService storeSearchService;

    @Mock
    private Logger logger;

    @InjectMocks
    private StoreSearchController storeSearchController;

    @Test
    public void testFetchStoresByZip() {
        List<String> stores = Arrays.asList("Store1", "Store2");
        Mockito.when(storeSearchService.fetchStoresByZipCode("12345")).thenReturn(stores);

        List<String> result = storeSearchController.fetchStoresByZip("12345");

        Mockito.verify(logger).log(StoreSearchController.class.getName());
        Assert.assertEquals(stores, result);
    }
}