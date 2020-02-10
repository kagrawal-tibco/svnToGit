package com.tibco.cep.runtime.service.loader;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.tibco.be.util.ByteArray;
import com.tibco.be.util.OversizeStringConstants;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jan 19, 2006
 * Time: 7:29:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class JarInputStreamReader_old {

    /**
     * return a class name to byte code map that contains in the JarInputStream
     * @param jis
     * @return
     * @throws Exception
     */
    static public Map read(JarInputStream jis) throws Exception {
        Map nameToByteCode = new HashMap ();
        ByteArray buf = new ByteArray(8192);
        for (JarEntry je = jis.getNextJarEntry(); je != null; je = jis.getNextJarEntry()) {
            buf.reset();
            if (je.isDirectory()) continue;
            String pathname = je.getName();
            if(pathname.endsWith(".class")) {
                pathname = getClassName(pathname);
                byte[] b = getBytes(jis, buf);
                nameToByteCode.put(pathname, b);
            } else if(pathname.equals(OversizeStringConstants.PROPERTY_FILE_NAME)) {
                byte[] b = getBytes(jis, buf);
                Properties props = new Properties();
                props.load(new ByteArrayInputStream(b));
                nameToByteCode.put(pathname, props);
            }
        }
        return nameToByteCode;
    }

    static private byte[] getBytes(JarInputStream jis, ByteArray buf) throws Exception {
        for(int x = jis.read(); x != -1; x = jis.read()) {
            buf.add((byte)x);
        }
        return buf.getValue();

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
}
