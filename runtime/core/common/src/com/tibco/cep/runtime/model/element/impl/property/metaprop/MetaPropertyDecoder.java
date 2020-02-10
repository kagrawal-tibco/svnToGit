package com.tibco.cep.runtime.model.element.impl.property.metaprop;

import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.helper.BitSet;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 1/18/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetaPropertyDecoder
{
    public static int HAS_DEFAULT = 6;
    public static int HIST_POLICY = 5;
    public static int HAS_HIST = 4;
    public static int IS_ARRAY = 3;
    public static int HAS_TYPE = 2;
    public static int IS_CONTAINED = 1;
    public static int MAINTAIN_RR = 0;

    private int idx;
    private final String s;

    public MetaPropertyDecoder(String str) {
        s = str;
    }

    public MetaProperty[] decode(ClassLoader classLoader) {
    	return decode(classLoader, false);
    }

    public MetaProperty[] decode(ClassLoader classLoader, boolean isAtomic) {
        //this comes from BaseConceptClassGenerator since MetaPropertyEncoder only does one MetaProperty at a time
        int numMprops = decodeInt();
        MetaProperty[] ret = new MetaProperty[numMprops];

        int mpropIdx = 0;
        int containedPropIdx = 0;
        while(idx < s.length()) {
            String name = decodeString();

            short bitSetAndPropType = decodeShort();
            byte propType = (byte)bitSetAndPropType;

            byte bitset = (byte)(bitSetAndPropType >> 8);
            //either 1 or 0
            byte historyPolicy = BitSet.checkBit(HIST_POLICY, bitset) ? (byte)1 : (byte)0;
            boolean isArray = BitSet.checkBit(IS_ARRAY, bitset);

            int historySize = 0;
            if(BitSet.checkBit(HAS_HIST, bitset)) historySize = decodeInt();

            Class type = null;
            if(BitSet.checkBit(HAS_TYPE, bitset))
				try {
					type = decodeClass(classLoader);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}

            Object defaultValue = null;
            if(BitSet.checkBit(HAS_DEFAULT, bitset)) defaultValue = decodeDefaultValue(propType);

            MetaProperty mprop;
            if(type != null) {
                boolean maintainRR = BitSet.checkBit(MAINTAIN_RR, bitset);
                int containedIdx = -1;
                if(BitSet.checkBit(IS_CONTAINED, bitset)) {
                    containedIdx = containedPropIdx++;
                }
                mprop = new MetaProperty(name, historyPolicy,  historySize, type
                        , maintainRR, containedIdx, propType, isArray, defaultValue, isAtomic);
            } else {
                mprop = new MetaProperty(name, historyPolicy,  historySize, propType, isArray, defaultValue, isAtomic);
            }
            ret[mpropIdx++] = mprop;
        }

        return ret;
    }

    private String decodeString() {
        int len = decodeInt();
        if(len == 0) return null;
        //make a copy since substring keeps the original string in memory
        String ret = new String(s.substring(idx, idx+len));
        idx+=len;
        return ret;
    }

    private Class decodeClass(ClassLoader ldr) throws ClassNotFoundException {
        String name = decodeString();
        if(name != null) {
            return Class.forName(name, false, ldr);
        } else {
            return null;
        }
    }

    private short decodeShort() {
        return (short)s.charAt(idx++);
    }

    private int decodeInt() {
        int ret = s.charAt(idx++) << 16;
        //mask high bits because of sign extension
        ret |= (0xFFFF & s.charAt(idx++));
        return ret;
    }

    private double decodeDouble() {
        long l = ((long)s.charAt(idx++)) << 48;
        //mask high bits because of sign extension
        l |= (0xFFFF & ((long)s.charAt(idx++))) << 32;
        l |= (0xFFFF & ((long)s.charAt(idx++))) << 16;
        l |= (0xFFFF & ((long)s.charAt(idx++)));
        return Double.longBitsToDouble(l);
    }

    private boolean decodeBool() {
        return s.charAt(idx++) != 0;
    }

    private Object decodeDefaultValue(byte propType) {
        //mask because of sign extension
        switch (propType & 0xFF) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return decodeBool();
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return decodeInt();
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return decodeDouble();
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return decodeString();
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                return java.util.Calendar.getInstance();
        }
        return null;
    }
}