package com.tibco.be.util.DotNetSupport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.tibco.util.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 22, 2006
 * Time: 5:07:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class DotNetConsolePrintStream extends PrintStream {
    private DotNetConsolePrintStream(String prefix) throws UnsupportedEncodingException
    {
        super(new DotNetConsoleOutputStream(prefix), true, "UTF-16");
    }
    
    private static class DotNetConsoleOutputStream extends OutputStream
    {
        private String prefix;
        public DotNetConsoleOutputStream(String prefix) {
            super();
            this.prefix = prefix;
        }
        
        private static ByteBuffer bb = new ByteBuffer();
        public void write(int b) throws IOException {
            bb.append(b);
        }
        public void flush() throws IOException {
            DotNetConsolePrintStream.nativeWrite(prefix + bb.toString("UTF-16"));
            bb.clear();
        }
    }
    
    private static PrintStream oldOut = null;
    private static PrintStream oldErr = null;
    public static void enable() {
        if(oldOut != null) return;
        
        oldOut = System.out;
        oldErr = System.err;
        
        try {
            System.setOut(new DotNetConsolePrintStream(""));
            System.setErr(new DotNetConsolePrintStream("err: "));
        } catch (UnsupportedEncodingException uee) {
            disable();
            uee.printStackTrace();
        }
    }
    
    public static void disable() {
        if(oldOut != null) {
            System.setOut(oldOut);
            oldOut = null;
        }
        if(oldErr != null) {
            System.setErr(oldErr);
            oldErr = null;
        }
    }
    
    native private static void nativeWrite(String str);
}
