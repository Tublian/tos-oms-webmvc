package com.oms.util;

import com.oms.util.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LoggerTest {

    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream errContent;

    @Before
    public void setUpStreams() {
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    public void testNullPathFallback() {
        Logger logger = new Logger();
        logger.setPath(null);
        logger.log("Test message");
        String output = errContent.toString();
        // Check that warning message is printed and fallback 'oms.log' is used
        assertTrue("Expected warning for null path", output.contains("Defaulting to oms.log"));
    }

    @Test
    public void testEmptyPathFallback() {
        Logger logger = new Logger();
        logger.setPath("");
        logger.log("Another test message");
        String output = errContent.toString();
        // Check that warning message is printed and fallback 'oms.log' is used
        assertTrue("Expected warning for empty path", output.contains("Defaulting to oms.log"));
    }
}
