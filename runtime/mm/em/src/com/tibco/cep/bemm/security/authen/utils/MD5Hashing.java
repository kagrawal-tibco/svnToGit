package com.tibco.cep.bemm.security.authen.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 2/11/11
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */

public class MD5Hashing {

    private static Logger logger = LogManagerFactory.getLogManager().getLogger(MD5Hashing.class);

    public static String getMD5Hash(String key) {
        if (key == null) {
            logger.log(Level.INFO, "It's illegal to hash a null value. Hashing empty String instead.");
            key = "";
        }


        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR,"Password encoded with unsupported coding scheme. Proceeding with encoded password.");
            return null;
        }

        md.update(key.getBytes());
        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();

    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        return hexString.toString();
    }

    public static void main(String[] args) {
        String key = null;
        System.err.println("Key = " + key + " MD5= " + getMD5Hash(key));
    }
}

