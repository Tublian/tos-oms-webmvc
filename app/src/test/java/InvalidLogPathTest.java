
package app;

import com.oms.util.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestPropertySource(properties = {"logger.path=/invalid/path/to/log.txt"})
public class InvalidLogPathTest {

    @Autowired
    private Logger logger;
    
    @Test
    public void testLoggerWithInvalidPath() {
        try {
            logger.log("API call simulation: logging test message");
        } catch (Exception e) {
            Assert.fail("Exception thrown during logger.log: " + e.getMessage());
        }
    }
}
