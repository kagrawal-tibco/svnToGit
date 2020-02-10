// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.schema.SmType;

/**
 * This class is a set of utility conversion functions for converting XML Schema
 * data types to and from their lexical representation.
 */
public class XmlConversions {
    /**
     * EVIL EVIL EVIL EVIL - that describes the SimpleDateFormat class.
     * This method returns an instanceof the class pre-configured for XML dates, however,
     * beware in using it because the parse() method is not thread safe (known issue in JDK, not documented before JDK1.4)
     */
    public static SimpleDateFormat createDefaultDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

/*    private static final SimpleDateFormat   defaultDateTimeFormatter
      = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");*/


    /**
     * Parses a date (according to XSD def) into Date.
     */
    public static Date getParsedDate(String strValue) throws IllegalArgumentException {
        try {
            // SimpleDateFormat is EVIL EVIL EVIL EVIL EVIL EVIL.  It is not thread safe!!!!! It keeps state during parse.
            SimpleDateFormat sdf = createDefaultDateFormatter();
            return sdf.parse(strValue);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date parse failed: "
                + e.getMessage());
        }
    }

    /**
     * Parses a date-time (according to XSD def) into Date.
     */
    public static Date getParsedDateTime(String strValue) /*throws IllegalArgumentException*/ {
//        try {
            return ISODateTimeParser.parseISO8601Date(strValue);
/*            return defaultDateTimeFormatter.parse(strValue);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date-time parse failed: "
                + e.getMessage());
        }*/
    }

    /**
     * Parses a Base64 binary (according to XSD def) into byte[]
     */
    public static byte[] getParsedBase64Binary(String strValue) {
        // HURRAY FOR CODE REUSE!
        return Base64Codec.decodeBase64(strValue);
    }

    public static byte[] getParsedHexBinary(String strValue) {
        char[] buf = strValue.toCharArray();
        if (buf.length%2!=0) {
            throw new IllegalArgumentException("Improperly encoded hex, odd # of characters");
        }
        byte[] b = new byte[buf.length/2];
        int len = buf.length;
        for (int i=0;i<len;i+=2) {
            b[i>>1] = (byte) (((hexval(buf[i])<<4)+hexval(buf[i+1])));
        }
        return b;
    }

    public static String toHexBinary(byte[] values) {
        char[] buf = new char[values.length*2];
        int len = values.length;
        for (int i=0;i<len;i++) {
            int b = values[i];
            if (b<0) {
                b=256+b;
            }
            buf[i*2] = hexchar(b/16);
            buf[i*2+1] = hexchar(b%16);
        }
        return new String(buf);
    }

    private static char hexchar(int value) {
        if (value<=9) {
            return (char)('0' + (char)value);
        }
        return (char)(('A'-10) + (char)value);
    }

    private static int hexval(char c) {
        if (c>='0' && c<='9') {
            return c-'0';
        }
        if (c>='A' && c<='F') {
            return c-('A'-10);
        }
        if (c>='a' && c<='f') { // according to schema, section 3.2.15 hexBinary, lower case a-f are ok, too.
            return c-('a'-10);
        }
        throw new IllegalArgumentException("Illegal hex character:" + c);
    }

    /**
     * Parses a string value to the appropriate Java basic object type
     * such as Integer, Boolean, Date etc.
     * @exception IllegalArgumentException if the string value is not
     * appropriate for the specified type
     */
    public static Object getParsedValue(SmType scalarType, String strValue)
    throws IllegalArgumentException {
//        return getParsedValue(XMetaData.convertToJavaTypeCode(scalarType),strValue);
        throw new RuntimeException();
    }

    public static Float convertFloat(String strValue) {
        try {
            Float f = new Float(strValue);
            // handle special cases:
            if (f.isInfinite()) {
                // My interpretation of the XSD spec is that numbers not in the float range are not valid, therefore
                // throw a format exception here. (Another possibility is to 'round' them to the nearest number, which would
                // be max float, but this seems stupid)
                throw new NumberFormatException("Out of range");
            }
            return f;
        } catch (NumberFormatException nfe) {
            String t = strValue.trim();
            if (t.equals("INF")) {
                return new Float(Float.POSITIVE_INFINITY);
            }
            if (t.equals("-INF")) {
                return new Float(Float.NEGATIVE_INFINITY);
            }
            if (t.equals("NaN")) {
                return new Float(Float.NaN);
            }
            throw nfe;
        }
    }

    public static boolean convertBoolean(String value) {
        if (value.equals("true")) {
            return true;
        }
        if (value.equals("false")) {
            return false;
        }
        // Bonus literals: (in the spec)
        if (value.equals("0")) {
            return false;
        }
        if (value.equals("1")) {
            return true;
        }
        throw new IllegalArgumentException("Expected true or false or 1 or 0, got " + value);
    }

    public static Double convertDouble(String strValue) {
        try {
            Double f = new Double(strValue);
            // handle special cases:
            if (f.isInfinite()) {
                // My interpretation of the XSD spec is that numbers not in the float range are not valid, therefore
                // throw a format exception here. (Another possibility is to 'round' them to the nearest number, which would
                // be max double, but this seems stupid)
                throw new NumberFormatException("Out of range");
            }
            return f;
        } catch (NumberFormatException nfe) {
            String t = strValue.trim();
            if (t.equals("INF")) {
                return new Double(Double.POSITIVE_INFINITY);
            }
            if (t.equals("-INF")) {
                return new Double(Double.NEGATIVE_INFINITY);
            }
            if (t.equals("NaN")) {
                return new Double(Double.NaN);
            }
            throw nfe;
        }
    }

    private static final Float FLOAT_NEGATIVE_ZERO = new Float(-0.0);

    public static String convertString(Float value) {
        if (value.isInfinite()) {
            if (value.floatValue()>0) {
                return "INF";
            } else {
                return "-INF";
            }
        }
        if (value.isNaN()) {
            return "NaN";
        }
        if (value.compareTo(FLOAT_NEGATIVE_ZERO)==0) {
            return "-0.0";
        }
        return value.toString();
    }

    private static final Double DOUBLE_NEGATIVE_ZERO = new Double(-0.0);

    public static String convertString(Double value) {
        if (value.isInfinite()) {
            if (value.floatValue()>0) {
                return "INF";
            } else {
                return "-INF";
            }
        }
        if (value.isNaN()) {
            return "NaN";
        }
        if (value.compareTo(DOUBLE_NEGATIVE_ZERO)==0) {
            return "-0.0";
        }
        return value.toString();
    }

    public static Object getWhitespaceNormalizedParsedValue(int javaTypeCode, int whitespaceStripCode, String strValue) {
//        if (whitespaceStripCode!=XMetaData.WHITESPACE_PRESERVE) {
//            // strip
//            if (strValue.indexOf('\t')>=0) {
//                strValue = strValue.replace('\t',' ');
//            }
//            if (strValue.indexOf('\r')>=0) {
//                strValue = strValue.replace('\r',' ');
//            }
//            if (strValue.indexOf('\n')>=0) {
//                strValue = strValue.replace('\n',' ');
//            }
//            // see if we need to collapse now...
//            if (whitespaceStripCode==XMetaData.WHITESPACE_COLLAPSE) {
//                strValue = strValue.trim();
//                if (strValue.indexOf("  ")>=0) {
//                    StringBuffer sb = new StringBuffer();
//                    char[] c = strValue.toCharArray();
//                    for (int i=0;i<c.length;i++) {
//                        if (c[i]!=' ' || i==0 || c[i-1]!=' ') {
//                            sb.append(c[i]);
//                        }
//                    }
//                    strValue = sb.toString();
//                }
//            }
//        }
//        return getParsedValue(javaTypeCode,strValue);
        throw new RuntimeException();
    }

    /**
     * Does NOT do whitespace normalization, assumes that has been done already.
     */
    public static Object getParsedValue(int javaTypeCode, String strValue)
    throws IllegalArgumentException {
//        switch (javaTypeCode) {
//            case XMetaData.TYPE_STRING:
//            case XMetaData.TYPE_WILDCARD: return strValue;
//            case XMetaData.TYPE_XDATA: throw new IllegalArgumentException("Can't convert an xdata from string");
//            case XMetaData.TYPE_DATE: return getParsedDate(strValue);
//            case XMetaData.TYPE_DATETIME: return getParsedDateTime(strValue);
//            case XMetaData.TYPE_BOOLEAN: {
//                if (strValue.equals("true")) {
//                    return Boolean.TRUE;
//                }
//                if (strValue.equals("false")) {
//                    return Boolean.FALSE;
//                }
//                // Bonus literals: (in the spec)
//                if (strValue.equals("0")) {
//                    return Boolean.FALSE;
//                }
//                if (strValue.equals("1")) {
//                    return Boolean.TRUE;
//                }
//                throw new IllegalArgumentException("Expected true or false or 1 or 0, got " + strValue);
//            }
//            case XMetaData.TYPE_HEX_BINARY: return XmlConversions.getParsedHexBinary(strValue);
//            case XMetaData.TYPE_BASE64_BINARY: return XmlConversions.getParsedBase64Binary(strValue);
//            case XMetaData.TYPE_BYTE: return new Byte(strValue);
//            case XMetaData.TYPE_SHORT: return new Short(strValue);
//            case XMetaData.TYPE_INTEGER: return new Integer(strValue);
//            case XMetaData.TYPE_LONG: return new Long(strValue);
//            case XMetaData.TYPE_FLOAT: return convertFloat(strValue);
//            case XMetaData.TYPE_DOUBLE: return convertDouble(strValue);
//            case XMetaData.TYPE_BIG_DECIMAL: return new java.math.BigDecimal(strValue);
//            case XMetaData.TYPE_BIG_INTEGER: return new java.math.BigInteger(strValue);
//            default: throw new IllegalArgumentException("Not yet implemented, convert for " + XMetaData.getTypeCodeString(javaTypeCode));
//        }
        throw new RuntimeException();
    }

    public static String convertDateToString(Date date) {
        return createDefaultDateFormatter().format(date);
    }

    public static String convertDateTimeToString(Date date) {
        return ISODateTimeParser.toISO8601String(date);
    }

    public static String convertToString(SmType scalarType, Object value) {
//        int tc = XMetaData.convertToJavaTypeCode(scalarType);
//        return convertToString(tc,value);
        throw new RuntimeException();
    }

    public static String convertToString(int javaTypeCode, Object value) {
//        if (value==null) {
//            return null;
//        }
//        switch (javaTypeCode) {
//            case XMetaData.TYPE_XDATA: return value.toString();
//            case XMetaData.TYPE_STRING: return value.toString();
//            case XMetaData.TYPE_BOOLEAN: return ((Boolean)value).booleanValue() ? "true" : "false";
//            case XMetaData.TYPE_BIG_INTEGER: return value.toString();
//            case XMetaData.TYPE_BIG_DECIMAL: return value.toString();
//            case XMetaData.TYPE_DATE: return createDefaultDateFormatter().format((Date) value);
//            case XMetaData.TYPE_DATETIME:
//                if (value instanceof TZDate) {
//                    return ((TZDate) value).getDateStr();
//                } else {
//                    return ISODateTimeParser.toISO8601String((Date) value);
//                }
//            case XMetaData.TYPE_BYTE: return value.toString();
//            case XMetaData.TYPE_SHORT: return value.toString();
//            case XMetaData.TYPE_INTEGER: return value.toString();
//            case XMetaData.TYPE_LONG: return value.toString();
//            case XMetaData.TYPE_FLOAT: return convertString((Float)value);
//            case XMetaData.TYPE_DOUBLE: return convertString((Double)value);
//            case XMetaData.TYPE_BASE64_BINARY: return Base64Codec.encodeBase64((byte[])value);
//            case XMetaData.TYPE_HEX_BINARY: return toHexBinary((byte[])value);
//            case XMetaData.TYPE_WILDCARD:
//                if (value instanceof Boolean) {
//                    return convertToString(XMetaData.TYPE_BOOLEAN, value);
//                } else if (value instanceof Date) {
//                    return convertToString(XMetaData.TYPE_DATETIME, value);
//                } else if (value instanceof byte[]) {
//                    return convertToString(XMetaData.TYPE_HEX_BINARY, value); // DK: what to do if base64?
//                } else {
//                    return value.toString();
//                }
//        }
//        throw new RuntimeException("Convert to string for type code " + XMetaData.getTypeCodeString(javaTypeCode) + " not yet implemented");
        throw new RuntimeException();
    }
}