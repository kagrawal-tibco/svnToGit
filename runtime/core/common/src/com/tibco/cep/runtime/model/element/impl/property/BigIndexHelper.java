package com.tibco.cep.runtime.model.element.impl.property;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 2/17/12
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BigIndexHelper {
    public static int makePropIdx(short _30_15, byte _14_7, byte _6_0) {
        return ((0xFFFF & _30_15) << 15) | ((0xFF & _14_7) << 7) | (0xFF & _6_0);
    }

    public static short make_30_15(int idx) {
        return (short)(idx >> 15);
    }

    public static byte make_14_7(int idx) {
        return (byte)(idx >> 7);
    }

    public static byte make_6_0(int idx) {
        return (byte)(idx & 0x7F);
    }
}
