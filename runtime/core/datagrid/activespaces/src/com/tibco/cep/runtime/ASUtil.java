package com.tibco.cep.runtime;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.util.annotation.Optional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Author: Ashwin Jayaprakash / Date: 2/23/11 / Time: 4:22 PM
*/
public class ASUtil {
    protected static final ConcurrentHashMap<String, String> encodedStringsCache =
            new ConcurrentHashMap<String, String>();

    protected static String bytesToHashMD5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(bytes, 0, bytes.length);

            byte[] md5 = md.digest();

            BigInteger number = new BigInteger(1, md5);

            return number.toString(16);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asFriendlyEncode(String s, @Optional Logger logger) {
        String newStr = encodedStringsCache.get(s);
        if (newStr != null) {
            return newStr;
        }
        else {
            newStr = s;
        }

        //Start UTF-8 hack
        try {
            String ascii = new String(s.getBytes("US-ASCII"), "US-ASCII");

            byte[] utf8Bytes = s.getBytes("UTF-8");
            String utf8 = new String(utf8Bytes, "UTF-8");

            if (!ascii.equals(utf8)) {
                newStr = bytesToHashMD5(utf8Bytes);
            }
        }
        catch (UnsupportedEncodingException e) {
            if (logger != null) {
                logger.log(Level.SEVERE, "Error occured while converting/sanitizing string [" + s +
                        "] encoding. Only US-ASCII and UTF-8 are supported", e);
            }
            else {
                e.printStackTrace();
            }
        }
        //End UTF-8 hack

        newStr = newStr.replace('.', '_')
        		.replace('$', '_')
        		.replace(' ', '_')
        		.replace('+', '_')
        		.replace('/', '_')
        		.replace('=', '_');

        if (!s.equals(newStr)) {
            if (logger != null) {
                logger.log(Level.FINE, "Re-encoding UTF-8 string [" + s + "] to ASCII [" + newStr + "]");
            }
        }

        String alreadyExists = encodedStringsCache.put(s, newStr);
        if (alreadyExists != null) {
            //Hmm...what are the chances of this happening?
        }

        return newStr;
    }

    /*
     * Sanitize the AS security file path
     * Substitute Global vars and Engine Name and replace slashes
     */
    public static String sanitizeSecurityFilePath(String fileRawPath,Cluster cluster) {
        String fileNewPath = null ;
        if (fileRawPath!=null) {
            fileNewPath = cluster.getRuleServiceProvider().getGlobalVariables().substituteVariables(fileRawPath).toString();
            fileNewPath = fileNewPath.replaceAll("%%EngineName%%", cluster.getRuleServiceProvider().getName());
            if (System.getProperty("os.name").startsWith("Windows")) {
                fileNewPath = fileNewPath.replace("/","\\");
            }
        }
        return fileNewPath;
    }
}
