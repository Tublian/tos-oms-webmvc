
package com.oms.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class LoggerTest {

    @Autowired
    private Logger logger;

    @Test
    public void testFallbackLoggingMechanism() throws Exception {
        File fallbackFile = new File("oms.log");
        if (fallbackFile.exists()) {
            fallbackFile.delete();
        }
        
        logger.setPath("nonexistent_folder/invalid-log.txt");
        logger.log("Test message");
        
        assertTrue(fallbackFile.exists());
        
        BufferedReader reader = new BufferedReader(new FileReader(fallbackFile));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        
        assertTrue(content.toString().contains("Warning: original log attempt failed."));
        
        fallbackFile.delete();
    }
}
