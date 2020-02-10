package com.tibco.be.functions.System;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 20, 2005
 * Time: 2:18:11 AM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "System.IO",
        synopsis = "System Wide Functions")

public class IOHelper {
    
    //TODO case-insensitive file system and case sensitive hashmap means fileOpen("C:/ASDF.TXT") and fileClose("c:/asdf.txt") will not close the file.
    volatile static ConcurrentHashMap<String, FWWrapper> openedFiles = null;

    @com.tibco.be.model.functions.BEFunction(
        name = "fileOpen",
        synopsis = "Open the specified file for writing if it is not already opened.",
        enabled = @Enabled(value=false),
        signature = "void fileOpen(String fileName, boolean append, boolean flush)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileName", type = "String", desc = "The name of the file to write to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "append", type = "boolean", desc = "Is this write to be treated as an append to an existing file?"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flush", type = "boolean", desc = "Should the data be flushed to the file immediately?")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "false if the specified file is not open for writing after the function has completed"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The specified file is opened / created automatically if necessary.\nappend and flush are only used to open / create the file \nand are ignored if the file is already opened.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean fileOpen(String fileName, boolean append) {
        try {
            return null != openFile(fileName, append);
        } catch (IOException ioe) {
            printStacktrace(ioe);
            return false;
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "fileCanWrite",
        synopsis = "Returns true if the specified file exists and is writable.",
        enabled = @Enabled(value=false),
        signature = "void fileCanWrite(String fileName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileName", type = "String", desc = "The name of the file to write to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the specified file exists and is writable"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The specified file is opened / created automatically if necessary.\nappend and flush are only used to open / create the file \nand are ignored if the file is already opened.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
   public static boolean fileCanWrite(String fileName) {
       return new File(fileName).canWrite();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileWrite",
        synopsis = "Write the data in <code>str</code> to the specified file.\nThe file is opened / created automatically if necessary.\nAppend and flush are only used to open / create the file and are ignored if the file\nis already opened.",
        signature = "void fileWrite (String fileName, String str, boolean append, boolean flush)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileName", type = "String", desc = "The name of the file to write to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "Data to be written."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "append", type = "boolean", desc = "Is this write to be treated as an append to an existing file?"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flush", type = "boolean", desc = "Should the data be flushed to the file immediately?")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Write the data in <code>str</code> to the specified file.\nThe file is opened / created automatically if necessary.\nAppend and flush are only used to open / create the file and are ignored if the file\nis already opened.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void fileWrite(String fileName, String str, boolean append, boolean flush) {
        try {
            FWriter fw = openFile(fileName, append);
            if(fw != null) fw.write(str, flush);
        } catch (IOException ioe) {
            printStacktrace(ioe);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileClose",
        synopsis = "Close the specified file <code>fileName</code> that was previously\nopened by System.fileWrite() or System.fileOpen().",
        signature = "void fileClose(String fileName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fileName", type = "String", desc = "The name of the file for closing.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.1",
        see = "fileWrite",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Close the specified file <code>fileName</code> that was previously\nopened by System.fileWrite() or System.fileOpen().",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void fileClose(String fileName) {
        try {
            closeFile(fileName);
        }
        catch (IOException ioe) {
            printStacktrace(ioe);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fileCloseAll",
        synopsis = "Close all files previously opened by System.fileWrite() or System.fileOpen().",
        enabled = @Enabled(value=false),
        signature = "void fileCloseAll()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "fileWrite",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Close all files previously opened by System.fileWrite() or System.fileOpen().",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void fileCloseAll() {
        _closeAllFiles();
    }

    private static void printStacktrace(Exception e) {
        LogManagerFactory.getLogManager().getLogger("debug").log(Level.ERROR, e, e.getMessage());
    }
    
    private static String getPath(String fileName) {
        return getPath(new File(fileName));
    }
    
    private static String getPath(File file) {
//        try {
//            file = file.getCanonicalFile();
//        } catch (IOException ioe){
//            file = file.getAbsoluteFile();
//        }
        return file.getPath();
    }

    private static FWriter openFile(String fileName, boolean append) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            File parent = file.getParentFile();
            if(parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
        }
        if(!file.canWrite()) return null;
            
        fileName = getPath(fileName);
        initOpenedFiles();
        FWWrapper fww = openedFiles.get(fileName);
        if(fww != null) return fww.getFWriter();
    
        fww = new FWInitWrapper(fileName, file, append, openedFiles);
        return fww.getFWriter();
    }
    
    private static FWriter getOpenedFile(String fileName) {
        if(openedFiles != null) {
            FWWrapper fww = openedFiles.get(fileName);
            if(fww != null) return fww.getFWriter();
        }
        return null;
    }
    
    private static void closeFile(String fileName) throws IOException {
        FWriter fw = getOpenedFile(fileName);
        if(fw != null) fw.close(openedFiles);
    }

    private static void initOpenedFiles() {
        if(openedFiles == null) {
            synchronized (IOHelper.class){
                if(openedFiles == null) {
                    openedFiles = new ConcurrentHashMap();
                    Runtime.getRuntime().addShutdownHook(new Thread () {
                        public void run() {
                            _closeAllFiles();
                        }
                    });
                }
            }
        }
    }

    private static void _closeAllFiles() {
        for(FWWrapper fww : openedFiles.values()) {
            try {
                if(fww != null) {
                    FWriter fw = fww.getFWriter();
                    if(fw != null) {
                        fw.close(openedFiles);
                    }
                }
            } catch (IOException ioe) {
                printStacktrace(ioe);
            }
        }
    }
    
    private static interface FWWrapper
    {
        FWriter getFWriter();
    }
    
    private static class FWInitWrapper implements FWWrapper
    {
        private static Object uninit_flag = new Object();
        String fileName;
        File file;
        boolean append;
        Object fw = uninit_flag;
        ConcurrentHashMap<String, FWWrapper> openedFiles;
        
        private FWInitWrapper(String fileName, File file, boolean append, ConcurrentHashMap<String, FWWrapper> openedFiles) {
            this.fileName = fileName;
            this.file = file;
            this.append = append;
            this.openedFiles = openedFiles;
        }
        
        public synchronized FWriter getFWriter() {
            if(fw == uninit_flag) {
                fw = openedFiles.putIfAbsent(fileName, this);
                if(fw == null) {
                    try {
                        fw = new FWriter(file, append);
                    } catch (IOException ioe) {
                        fw = null;
                        openedFiles.remove(fileName);
                        printStacktrace(ioe);
                    }
                } else {
                    fw = ((FWWrapper)fw).getFWriter();
                }
            }
            return (FWriter) fw;
        }
    }
    
    
    private static class FWriter implements FWWrapper
    {
        FileWriter fw = null;
        
        private FWriter(File file, boolean append) throws IOException {
            //synchronize to flush fw to shared memory
            synchronized(this) {
                fw = new FileWriter(file, append);
            }
        }
        
        synchronized private void write(String str, boolean flush) throws IOException {
            if(fw != null) {
                fw.write(String.valueOf(str));
                if(flush) fw.flush();
            }
        }

        synchronized private void close(Map openedFiles) throws IOException {
            if(fw != null) {
                fw.flush();
                fw.close();
                fw = null;
                openedFiles.remove(this);
            }
        }
        
        public FWriter getFWriter() {
            return this;
        }
    }
}
