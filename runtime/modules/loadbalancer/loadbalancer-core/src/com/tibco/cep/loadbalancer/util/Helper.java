package com.tibco.cep.loadbalancer.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/*
* Author: Ashwin Jayaprakash / Date: Mar 18, 2010 / Time: 2:47:28 PM
*/

public class Helper {
    /**
     * {@value}
     */
    public static final String JMX_ROOT_NAME = "com.tibco.be:type=Agent,subType=LoadBalancer";

    private static byte[] $asBytes(Object object) {
        if (object instanceof byte[]) {
            return (byte[]) object;
        }
        else if (object instanceof String) {
            return ((String) object).getBytes();
        }

        return object.toString().getBytes();
    }

    public static String $hashMD5(Object object) {
        try {
            byte[] buf = $asBytes(object);

            return $bytesToHashMD5(buf, 0, buf.length);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String $bytesToHashMD5(byte[] bytes, int offset, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(bytes, offset, length);

            byte[] md5 = md.digest();

            BigInteger number = new BigInteger(1, md5);

            return number.toString(16);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer $hashMurmur(Object object) {
        try {
            byte[] buf = $asBytes(object);

            return $bytesToHashMurmur(buf, 0xDEDE48A1);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int $bytesToHashMurmur(byte[] bytes, int seed) {
        //todo Attribute ASL: http://www.getopt.org/murmur/MurmurHash.java this is for testing only.

        if (Flags.DEBUG) {
            int largePrime = 0x5bd1e995;
            int r = 24;

            int h = seed ^ bytes.length;

            int len = bytes.length;
            int len_4 = len >> 2;

            for (int i = 0; i < len_4; i++) {
                int i_4 = i << 2;
                int k = bytes[i_4 + 3];
                k = k << 8;
                k = k | (bytes[i_4 + 2] & 0xff);
                k = k << 8;
                k = k | (bytes[i_4 + 1] & 0xff);
                k = k << 8;
                k = k | (bytes[i_4 + 0] & 0xff);
                k *= largePrime;
                k ^= k >>> r;
                k *= largePrime;
                h *= largePrime;
                h ^= k;
            }

            int len_m = len_4 << 2;
            int left = len - len_m;

            if (left != 0) {
                if (left >= 3) {
                    h ^= (int) bytes[len - 3] << 16;
                }
                if (left >= 2) {
                    h ^= (int) bytes[len - 2] << 8;
                }
                if (left >= 1) {
                    h ^= (int) bytes[len - 1];
                }

                h *= largePrime;
            }

            h ^= h >>> 13;
            h *= largePrime;
            h ^= h >>> 15;

            return h;
        }
        else {
            throw new UnsupportedOperationException("Works in Debug mode only");
        }
    }

    public static void $sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }
}
