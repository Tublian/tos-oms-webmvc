
package com.oms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private String path;
    private FileOutputStream os;

    public Logger() {
        this.path = "oms.log";
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void log(String msg) {
        try {
            if (os == null) {
                if (path == null || !new java.io.File(path).canWrite()) {
                    path = "oms.log";
                }
                os = new FileOutputStream(path, true);
            }
            PrintWriter pw = new PrintWriter(os);
            pw.println("[" + this.getClass().getName() + "] " + msg);
            pw.flush();
            os.flush();
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}