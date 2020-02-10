package com.tibco.cep.bpmn.runtime.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.tibco.be.util.OversizeStringConstants;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jan 19, 2006
 * Time: 7:29:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEJarInputStreamReader
{

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }

    /**
     * return a class name to byte code map that contains in the JarInputStream
     * @param jis
     * @return
     * @throws Exception
     */
    static public Map read(JarInputStream jis) throws Exception {
        Map nameToByteCode = new HashMap ();
        final byte[][] bufHandle = new byte[][]{new byte[8192]};
        for (JarEntry je = jis.getNextJarEntry(); je != null; je = jis.getNextJarEntry()) {
            if (je.isDirectory()) continue;
            String pathname = je.getName();
            if(pathname.endsWith(".class")) {
                pathname = getClassName(pathname);
                byte[] b = getBytes(bufHandle, jis);
                nameToByteCode.put(pathname, b);
            } else if(pathname.equals(OversizeStringConstants.PROPERTY_FILE_NAME)) {
                byte[] b = getBytes(bufHandle, jis);
                nameToByteCode.put(pathname, b);
            }
        }
        return nameToByteCode;
    }

    static private byte[] getBytes(byte[][] bufHandle, JarInputStream jis) throws IOException {
        byte[] buf = bufHandle[0];
        int totalBytes = 0;
        while(true) {
            int read = 0;
            read = jis.read(buf, totalBytes, buf.length - totalBytes);
            if(read == -1) {
                byte[] ret = new byte[totalBytes];
                System.arraycopy(buf, 0, ret, 0, totalBytes);
                return ret;
            } else {
                totalBytes += read;
                if(totalBytes == buf.length) {
                    byte[] tmp = new byte[buf.length * 2];
                    System.arraycopy(buf, 0, tmp, 0, buf.length);
                    buf = tmp;
                    bufHandle[0] = tmp;
                }
            }
        }
    }

    static private String getClassName(String fullpath) {
        StringBuffer classBuf = new StringBuffer();
        String[] token = fullpath.split("/|\\.|\\\\");

        if (token ==null || token.length == 0) return fullpath;
        int count = 0;
        if (!("class".equalsIgnoreCase(token[token.length-1]))) {
            count = token.length;
        }
        else {
            count = token.length - 1;
        }
        classBuf.append(token[0]);
        for (int i=1; i < count; i++) {
            classBuf.append(".");
            classBuf.append(token[i]);
        }
        return classBuf.toString();
    }
    
    public static void main(String[] args) throws Exception {
        java.io.File file = new java.io.File(args[0]);
        JarInputStream jis;
        long start;
        
        jis = new JarInputStream(new java.io.FileInputStream(file));
        start = System.currentTimeMillis();
        BEJarInputStreamReader.read(jis);
        System.out.println("new time " + (System.currentTimeMillis() - start));

        jis = new JarInputStream(new java.io.FileInputStream(file));
        start = System.currentTimeMillis();
        BEJarInputStreamReader.read(jis);
        System.out.println("old time " + (System.currentTimeMillis() - start));
    }
}