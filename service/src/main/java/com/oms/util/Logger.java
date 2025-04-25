
package com.oms.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class Logger {
    private FileOutputStream outputStream;

    public void setPath(String path) {
        try {
            outputStream = new FileOutputStream(path, true);
        } catch(IOException e) {
            System.err.println("Failed to initialize FileOutputStream with path: " + path + ". Error: " + e.getMessage());
            try {
                outputStream = new FileOutputStream("oms.log", true);
            } catch(IOException ex) {
                System.err.println("Failed to initialize FileOutputStream with default path oms.log. Error: " + ex.getMessage());
            }
        }
    }

    public void log(String message) {
        try {
            outputStream.write((message + "\n").getBytes());
        } catch(IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }
}
