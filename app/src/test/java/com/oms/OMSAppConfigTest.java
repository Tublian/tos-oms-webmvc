package com.oms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OMSAppConfigTest {

    @InjectMocks
    private OMSAppConfig omsAppConfig;

    @Test
    public void testConfig() {
        Assert.assertNotNull(omsAppConfig);
    }
}