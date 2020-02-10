package com.tibco.cep.kernel.service.profiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 12, 2008
 * Time: 2:18:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVWriter extends ProfileWriter {

    static final public String DEFAULT_FILE_PREFIX = "be-profile";
    PrintWriter writer = null;
    String fileName;
    
    public CSVWriter(int level, String delim) {
        super(level, delim);
    }

    public CSVWriter(int level, String delim, String name) throws IOException {
        super(level, delim);

        this.fileName = name;
        // extra protection as the name should have set
        if (name == null || name.trim().length() == 0) {
            fileName = DEFAULT_FILE_PREFIX + ".xls";
        }
        File file = new File(fileName);

        if (file.exists()) { // add current timestemp to the file name
            String timeStamp = new SimpleDateFormat("MMddyyyy-HHmmss").format(new Date());
            int index = fileName.lastIndexOf('.');
            if (index != -1) {
                StringBuilder sb = new StringBuilder(fileName.substring(0, index));
                sb.append('_');
                sb.append(timeStamp);
                sb.append(fileName.substring(index));
                fileName = sb.toString();
            } else {
                fileName = new StringBuilder(fileName).append('_').append(timeStamp).toString();
            }
            file = new File(fileName);
        }

        if (!file.createNewFile()) {
            throw new IOException("Unable to create file: " + file.getAbsolutePath()
                    + " for writing.");
        }

       writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
       //return file;
    }

    public void write(String out) throws IOException {
        writer.print(out);
        writer.println();
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

    public String getFileName() {
        return fileName;
    }
}
