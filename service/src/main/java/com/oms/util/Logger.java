
package com.oms.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private String path;
    private FileOutputStream os;

    public void setPath(String path) {
        this.path = path;
    }

    public void log(String msg) {
        try {
            if (os == null) {
                if (path == null) {
                    //tests
                    return;
                }
                try {
                    os = new FileOutputStream(path, true);
                } catch (IOException primaryEx) {
                    System.err.println("Failed to open primary log file: " + path + ". Using fallback file oms.log");
                    try {
                        os = new FileOutputStream("oms.log", true);
                    } catch (IOException fallbackEx) {
                        System.err.println("Failed to open fallback log file oms.log: " + fallbackEx.getMessage());
                        return;
                    }
                }
            }
            PrintWriter pw = new PrintWriter(os);
            pw.println(msg);
            pw.flush();
            os.flush();
        } catch (IOException e) {
            System.err.println("Error occurred during logging: " + e.getMessage());
        }
    }
}
