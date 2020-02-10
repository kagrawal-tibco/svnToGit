
/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 5, 2003
 * Time: 2:34:15 PM
 */
package com.tibco.be.util;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Random;

public class GUIDGenerator {
    protected static final Random s_random=new Random();
    static MessageDigest md5 = null ;
    static String hostid = null;
    static int localAddress = -1;
    static {
        try {
            localAddress = InetAddress.getLocalHost().hashCode();
            hostid = InetAddress.getLocalHost().toString();

            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    /**
     * Generates an URI with reasonable degree of uniqueness.
     *
     * @return                          unique URI
     */
    public static String getUniqueURI() {
    	int rand = s_random.nextInt();
        return System.currentTimeMillis() + (rand < 0 ? "":"-") + rand;
    }


    public  static String getGUID()  {
        try {
            md5 = MessageDigest.getInstance("MD5");

            StringBuffer sb = new StringBuffer();
            long time = System.currentTimeMillis();
            long rand = s_random.nextLong();
            sb.append(hostid);
            sb.append("-");
            sb.append(Long.toString(time));
            sb.append("-");
            sb.append(Long.toString(rand));

            md5.update(sb.toString().getBytes());

            byte[] array = md5.digest();
            sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }

            return format(sb.toString());
        }
        catch (Exception e) {
            return getUniqueURI();
        }



    }


/*
* Convert to the standard format for GUID
* Example: D2F0BBAC-EFDC-08D1-7EFF-FF60E907A91C
*/

    public static String format(String s) {
        String raw = s.toUpperCase();
        StringBuffer sb = new StringBuffer();
        sb.append(raw.substring(0, 8));
        sb.append("-");
        sb.append(raw.substring(8, 12));
        sb.append("-");
        sb.append(raw.substring(12, 16));
        sb.append("-");
        sb.append(raw.substring(16, 20));
        sb.append("-");
        sb.append(raw.substring(20));

        return sb.toString();
    }





}