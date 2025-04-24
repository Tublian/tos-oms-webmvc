
package com.oms.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class Logger {

    private String path;

    public Logger() {
        this.path = "oms.log";
    }

    public void setPath(String path) {
        try {
            new FileOutputStream(path).close();
            this.path = path;
        } catch (IOException e) {
            this.path = "oms.log";
        }
    }

    public void log(String msg) {
        try {
            FileOutputStream fos = new FileOutputStream(this.path, true);
            fos.write(msg.getBytes());
            fos.close();
        } catch (IOException e) {
            System.err.println("Error writing log message: " + e.getMessage());
        }
    }
}
