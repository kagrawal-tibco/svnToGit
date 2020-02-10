package com.tibco.cep.loadbalancer.impl.message;

import static com.tibco.cep.loadbalancer.util.Helper.$hashMD5;
import static com.tibco.cep.loadbalancer.util.Helper.$hashMurmur;

import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.util.Flags;

/*
* Author: Ashwin Jayaprakash / Date: Jul 7, 2010 / Time: 5:11:26 PM
*/

public class DistributionKeyMaker {
    /**
     * {@value}
     */
    public static final int DEFAULT_NUM_KEYS = 4096 * 3;

    /**
     * @param seed
     * @param numKeysToMake 1 or higher.
     * @return
     */
    public static DistributionKey[] $makeKeys(Object seed, int numKeysToMake) {
        final String s = seed.hashCode() + "." + seed;
        final int hash = s.hashCode();
        final byte[] bytes = s.getBytes();

        if (numKeysToMake == 1) {
            byte[] b = bytes.clone();

            $spice(b, hash);

            return $asKeys(b);
        }
        else {
            Object[] others = new Object[numKeysToMake - 1];

            for (int i = others.length - 1, j = 0; i >= 0; i--, j++) {
                others[j] = bytes.clone();

                $spice((byte[]) others[j], hash ^ i);
            }

            $spice(bytes, hash);

            return $asKeys(bytes, others);
        }
    }

    private static void $spice(byte[] bytes, int salt) {
        bytes[Math.abs(salt * 31) % bytes.length] = (byte) ((salt >>> 24) & 0xFF);
        bytes[Math.abs(salt * 113) % bytes.length] = (byte) ((salt >>> 16) & 0xFF);
        bytes[Math.abs(salt * 569) % bytes.length] = (byte) ((salt >>> 8) & 0xFF);
        bytes[Math.abs(salt * 983) % bytes.length] = (byte) ((salt >>> 0) & 0xFF);

        bytes[Math.abs(salt ^ 31) % bytes.length] = (byte) ((salt >>> 0) & 0xFF);
        bytes[Math.abs(salt ^ 113) % bytes.length] = (byte) ((salt >>> 8) & 0xFF);
        bytes[Math.abs(salt ^ 569) % bytes.length] = (byte) ((salt >>> 16) & 0xFF);
        bytes[Math.abs(salt ^ 983) % bytes.length] = (byte) ((salt >>> 24) & 0xFF);
    }

    /**
     * Keys created like this can only be used with other keys created with a {@link String} type.
     *
     * @param firstKey
     * @param remainingKeys
     * @return
     */
    public static DistributionKey[] $asKeys(Object firstKey, Object... remainingKeys) {
        DistributionKey[] bootstrapKeys = new DistributionKey[1 + remainingKeys.length];

        int x = 0;

        //First key.
        Comparable hash = Flags.DEBUG ? $hashMurmur(firstKey) : $hashMD5(firstKey);
        bootstrapKeys[x++] = new DefaultDistributionKey<Comparable>(hash);

        //Remaining optional keys.
        for (Object key : remainingKeys) {
            hash = Flags.DEBUG ? $hashMurmur(key) : $hashMD5(key);
            bootstrapKeys[x++] = new DefaultDistributionKey<Comparable>(hash);
        }

        return bootstrapKeys;
    }
}
