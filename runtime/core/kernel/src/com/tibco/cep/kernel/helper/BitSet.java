package com.tibco.cep.kernel.helper;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 1/23/12
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BitSet {
    public static boolean isBitSet(int[] dirtyBits, int index) {
        return 0 != (dirtyBits[index / 32] & (1 << (index % 32)));
    }

    public static void setBit(int[] dirtyBits, int index) {
        dirtyBits[index / 32] |= 1 << (index % 32);
    }
    
    public static void clearBit(int[] dirtyBits, int index) {
        dirtyBits[index / 32] &= ~(1 << (index % 32));
    }

    public static boolean isBitSet_Safe(int[] dirtyBits, int index, boolean defVal) {
        int arrIdx = index / 32;
        if(dirtyBits == null || arrIdx >= dirtyBits.length || arrIdx < 0) {
            return defVal;
        }
        return 0 != (dirtyBits[arrIdx] & (1 << (index % 32)));
    }

    public static void setBit_Safe(int[] dirtyBits, int index) {
        int arrIdx = index / 32;
        if(dirtyBits != null && arrIdx < dirtyBits.length && arrIdx >= 0) {
            dirtyBits[arrIdx] |= 1 << (index % 32);
        }
    }

    public static byte setBit(int bit, byte bitset) {
        assert bit < 8;
        bitset |= 1 << bit;
        return bitset;
    }

    public static boolean checkBit(int bit, byte bitset) {
        assert bit < 8;
        return (bitset & (1 << bit)) != 0;
    }

    public static boolean isAnyBitSet(int[] dirtyBitArray) {
        for (int dirtyBits : dirtyBitArray) {
            if (dirtyBits != 0) return true;
        }
        return false;
    }
}
