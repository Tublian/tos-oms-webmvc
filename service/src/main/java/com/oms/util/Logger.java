
package com.oms.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private String path;
    private FileOutputStream os;
    private String fallbackLogFile;

    public void setPath(String path) {
        this.path = path;
    }
    
    public void setFallbackLogFile(String fallbackLogFile) {
        this.fallbackLogFile = fallbackLogFile;
    }

    public void log(String msg) {
        try {
            if (os == null) {
                if (path == null) {
                    //tests
                    return;
                }
                os = new FileOutputStream(path, true);
            }
            PrintWriter pw = new PrintWriter(os);
            pw.println(msg);
            pw.flush();
            os.flush();
        } catch (IOException e) {
            os = null;
            if (fallbackLogFile != null) {
                setPath(fallbackLogFile);
            } else {
                setPath("oms.log");
            }
            try {
                FileOutputStream fallbackOut = new FileOutputStream(path, true);
                PrintWriter pwFallback = new PrintWriter(fallbackOut);
                pwFallback.println("Warning: original log attempt failed.");
                pwFallback.flush();
                fallbackOut.flush();
                fallbackOut.close();
            } catch (IOException ex) {
                // empty
            }
        }
    }
}
