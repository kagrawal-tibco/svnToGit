package com.tibco.be.parser.codegen;

import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.HAS_DEFAULT;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.HAS_HIST;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.HAS_TYPE;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.HIST_POLICY;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.IS_ARRAY;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.IS_CONTAINED;
import static com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder.MAINTAIN_RR;

import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 1/18/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetaPropertyEncoder
{
    //just for use by the test in main()
    private static final boolean dontEscape = false;

    public static String encode(String name, byte historyPolicy, int historySize, byte propType, boolean isArray, String defaultValue) {
        return encode(name, historyPolicy, null, historySize, false, false, propType, isArray, defaultValue);
    }

    public static String encode(String name, byte historyPolicy, String typeName, int historySize,
                 boolean maintainReverseRefs, boolean isContained, byte propType, boolean isArray, String defaultValue)
    {
        StringBuilder bldr = new StringBuilder(512);
        appendString(name, bldr);

        //bitset of : has type, is array, has history, history policy, is CC, maintain RR
        byte bitset = 0;
        //either 1 or 0
        if(historyPolicy == 1) bitset = BitSet.setBit(HIST_POLICY, bitset);
        if(historySize > 0) bitset = BitSet.setBit(HAS_HIST, bitset);
        if(isArray) bitset = BitSet.setBit(IS_ARRAY, bitset);
        if(defaultValue != null && defaultValue.length() > 0) bitset = BitSet.setBit(HAS_DEFAULT, bitset);

        if(typeName != null) {
            bitset = BitSet.setBit(HAS_TYPE, bitset);
            if(isContained) bitset = BitSet.setBit(IS_CONTAINED, bitset);
            if(maintainReverseRefs) bitset = BitSet.setBit(MAINTAIN_RR, bitset);
        }

        short bitSetAndPropType = shrt(bitset, propType);
        appendShort(bitSetAndPropType, bldr);
        if(historySize > 0) appendInt(historySize, bldr);
        if(typeName != null) appendString(typeName, bldr);
        if(defaultValue != null && defaultValue.length() > 0) appendDefaultValue(defaultValue, propType, bldr);

        return bldr.toString();
    }

    public static String encodeAsConstructor(String name, int historyPolicy, String typeName, int historySize
                         , boolean maintainReverseRefs, boolean isContained, int propType, boolean isArray
                         , String defaultValue, boolean isAtomic)
    {
        return String.format("new %s(\"%s\", (byte)%s, %s, %s.class, %s, %s, (byte)%s, %s, %s, %s)%s"
                , MetaProperty.class.getName(), name, historyPolicy, historySize
                , typeName , maintainReverseRefs, isContained ? "%s" : -1, propType, isArray, defaultValue, isAtomic, isContained ? "c" : "");
    }

    public static String encodeAsConstructor(String name, int historyPolicy, int historySize, int propType
    		, boolean isArray, String defaultValue, boolean isAtomic)
    {
        return String.format("new %s(\"%s\", (byte)%s, %s, (byte)%s, %s, %s, %s)"
                , MetaProperty.class.getName(), name, historyPolicy, historySize, propType, isArray, defaultValue, isAtomic);
    }

    public static int appendWithCCIdx(StringBuilder body, String mprop, int ccIdx) {
        if(mprop.endsWith("c")) {
            body.append(String.format(mprop.substring(0, mprop.length()-1), ccIdx));
            ccIdx++;
        } else {
            body.append(mprop);
        }
        return ccIdx;
    }

    private static void appendString(String val, StringBuilder bldr) {
        if(val == null || val.length() == 0) {
            appendInt(0, bldr);
        } else {
            appendInt(val.length(), bldr);
            for(int ii = 0; ii < val.length(); ii++) {
                appendChar(val.charAt(ii), bldr);
            }
        }
    }

    private static void appendShort(short val, StringBuilder bldr) {
        appendChar((char)val, bldr);
    }

    public static void appendInt(int val, StringBuilder bldr) {
        appendChar((char)(val >>> 16), bldr);
        appendChar((char)(val & 0xFFFF), bldr);
    }

    private static void appendDouble(double val, StringBuilder bldr) {
        long l = Double.doubleToRawLongBits(val);
        appendChar((char)(l >>> 48), bldr);
        appendChar((char)((l >>> 32) & 0xFFFF), bldr);
        appendChar((char)((l >>> 16) & 0xFFFF), bldr);
        appendChar((char)(l & 0xFFFF), bldr);
    }

    private static void appendBool(boolean val, StringBuilder bldr) {
        appendChar(val ? (char)1 : (char)0, bldr);
    }

    private static void appendChar(final char chr, final StringBuilder bldr) {
        if(dontEscape) {
            bldr.append(chr);
            return;
        }
        _appendChar(chr, bldr);
    }

    private static void _appendChar(final char chr, final StringBuilder bldr) {
        switch(chr) {
        	case '\0':
           		//this is an octal escape.  The number following the backslash can be one to three digits 
        		//but since the following characters are
        		//not predictable, three must be used to make it non-ambiguous
        		bldr.append("\\000");
        		break;
            case '\b' :
                bldr.append("\\b");
                break;
            case '\t' :
                bldr.append("\\t");
                break;
            case '\n' :
                bldr.append("\\n");
                break;
            case '\f' :
                bldr.append("\\f");
                break;
            case '\r' :
                bldr.append("\\r");
                break;
            case '"' :
                bldr.append("\\\"");
                break;
            case '\\' :
                bldr.append("\\\\");
                break;
            default:
                bldr.append(chr);
                break;
        }
    }

    private static short shrt(byte a, byte b) {
        return (short)(((a << 8) & 0xFF00) | (b & 0xFF));
    }

    private static void appendDefaultValue(String defVal, int propType, StringBuilder bldr) {
        switch (propType) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                appendBool(Boolean.parseBoolean(defVal), bldr);
                break;
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                try {
                    appendInt(Integer.parseInt(defVal), bldr);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException(nfe);
                }
                break;
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                try {
                    appendDouble(Double.parseDouble(defVal), bldr);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException(nfe);
                }
                break;
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                appendString(defVal, bldr);
                break;
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                //only option is java.util.Calendar.getInstance()
                //so HAS_DEFAULT plus propType is enough to know what to do
        }
    }

    //todo parse the default value with the parser to see if it is constant or an expression
    public static String getDefaultValue(PropertyDefinition pd) {
        String defVal = pd.getDefaultValue();
        if (defVal == null || defVal.length() == 0) return null;
        switch (pd.getType()) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return Boolean.valueOf(defVal).toString();
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                try {
                    return Integer.toString(Integer.parseInt(defVal));
                } catch (NumberFormatException nfe) {
                    //todo debug code (enforce this in the gui?)
                    return null;
                    //throw new IllegalArgumentException("NumberFormat exception while parsing integer default value for property definition: " + pd.getName());
                }
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                try {
                    return Double.toString(Double.parseDouble(defVal));
                } catch (NumberFormatException nfe) {
                    //todo debug code
                    return null;
                    //throw new IllegalArgumentException("NumberFormat exception while parsing double default value for property definition: " + pd.getName());
                }
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return "\"" + defVal + "\"";
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                //todo implement default values for concept properties
                return null;
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                if (defVal.equalsIgnoreCase("SYSDATE") || defVal.equalsIgnoreCase("CURRENTTIMESTAMP")) {
                    return "java.util.Calendar.getInstance()";
                } else {
                    return null;
                }
            default:
                return null;
        }
    }

//    public static void main(String[] args) throws Exception {
//        Random r = new Random();
//        int size = 100000;
//        MetaProperty[] mpropsA = new MetaProperty[size];
//        String[] encoded = new String[size];
//        int len = 0;
//        int ccidx=0;
//        for(int ii = 0; ii < size; ii++) {
//            String name = randString(r);
//            byte policy = (byte)r.nextInt(2);
//            int historySize = Math.abs(r.nextInt());
//            Class clz = r.nextBoolean() ? EntityDaoConfig.class : null;
//            byte propType = (byte)(r.nextInt(8));
//            boolean isArray = r.nextBoolean();
//            if(clz != null) {
//                boolean maintainRR = r.nextBoolean();
//                int ccIdx = r.nextBoolean() ? -1 : ccidx++;
//                mpropsA[ii] = new MetaProperty(name, policy, historySize, clz, maintainRR, ccIdx, propType, isArray);
//                encoded[ii] = EncoderTest.encode(name, policy, clz.getName(), historySize, maintainRR, ccIdx != -1, propType, isArray);
//
//            } else {
//                mpropsA[ii] = new MetaProperty(name, policy, historySize, propType, isArray);
//                encoded[ii] = EncoderTest.encode(name, policy, historySize, propType, isArray);
//
//            }
//            len += encoded[ii].length();
//        }
//
//        StringBuilder bldr = new StringBuilder(len + size + 2);
//        EncoderTest.appendInt(size, bldr);
//        for(String s : encoded) {
//            bldr.append(s);
//        }
//        String s = bldr.toString();
//        bldr = null;
//        MetaProperty[] mpropsB = new MetaPropertyDecoder(s).decode(EncoderTest.class.getClassLoader());
//        s = null;
//
//        for(int ii = 0; ii < size; ii++) {
//            StringBuilder err = null;
//            MetaProperty a = mpropsA[ii];
//            MetaProperty b = mpropsB[ii];
//            String encod = encoded[ii];
//            if(a.getMaintainReverseRefs() != b.getMaintainReverseRefs()) {
//                err = err(err, a.getMaintainReverseRefs(), b.getMaintainReverseRefs(), "maintain rr");
//            }
//            if(a.getContainedPropIndex() != b.getContainedPropIndex()) {
//                err = err(err, a.getContainedPropIndex(), b.getContainedPropIndex(), "cc idx");
//            }
//            if(a.getHistoryPolicy() != b.getHistoryPolicy()) {
//                err = err(err, a.getHistoryPolicy(), b.getHistoryPolicy(), "history policy");
//            }
//            if(a.getHistorySize() != b.getHistorySize()) {
//                err = err(err, a.getHistorySize(), b.getHistorySize(), "history size");
//            }
//            if(!String.valueOf(a.getName()).equals(String.valueOf(b.getName()))) {
//                err = err(err, a.getName(), b.getName(), "name");
//            }
//            if(a.getType() != b.getType()) {
//                err = err(err, a.getType(), b.getType(), "type");
//            }
//            if(a.propType != b.propType) {
//                err = err(err, a.propType, b.propType, "propType");
//            }
//            if(a.isArray() != b.isArray()) {
//                err = err(err, a.isArray(), b.isArray(), "isArray");
//            }
//
//            if(err != null && err.length() > 0) {
//                for(int jj = 0; jj < encod.length(); jj++) {
//                    _appendChar(encod.charAt(jj), err);
//                }
//                err.append("\n");
//                System.out.println(err.toString());
//            }
//        }
//    }
//
//    private static StringBuilder err(StringBuilder err, Object a, Object b, String name) {
//        if(err == null) err = new StringBuilder();
//        err.append(name).append(" : ").append(a).append(" : ").append(b).append("\n");
//        return err;
//    }
//
//    private static String randString(Random r) {
//        int len = r.nextInt(200);
//        if(len == 0) {
////            System.out.println("tested null type name");
//            return null;
//        }
//        char[] chr = new char[len];
//        for(int ii = 0; ii < len; ii++) {
//            char ch = (char)r.nextInt(-Short.MIN_VALUE);
//            if(r.nextBoolean()) ch = (char)-ch;
//            chr[ii] = ch;
//        }
//        return new String(chr);
//    }
}