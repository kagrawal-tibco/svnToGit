package com.tibco.be.util;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.util.StringUtilities;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 11:19:57 AM
 * To change this template use Options | File Templates.
 */

/**
 * This class derives most of its functionality from XMLSDK Stringutilities class
 */
public class BEStringUtilities extends StringUtilities {


    public static final String ARG_BEGIN_DELIMITER = "{{";
    public static final String ARG_END_DELIMITER = "}}";
    public static String XSLTSCHEME="xslt://";

    /** Utility method to replace character entity embedded in stream with
     the appropriate character */
    public static String unescapeDoubleQuotes(String s){
        int pos = 0;
        StringBuffer sb = new StringBuffer();
        while (true){
            int f = s.indexOf("&quot;", pos);
            if (f == -1) break;
            sb.append(s.substring(pos, f) + "'");
            pos = f + 6;
        }
        sb.append(s.substring(pos));
        return sb.toString();
    }


    final static String octalDigits = "01234567";
    final static String hexDigits = "0123456789abcdefABCDEF";
    final static String escChars = "\n\t\b\r\f\\\'\"";
    final static String unescChars = "ntbrf\\'\"";

    public static String unescape( String cstring )
    {
        if (cstring == null)
            return cstring;

        int len = cstring.length();
        StringBuffer sb = new StringBuffer(len);
        int val;
        int unesc;

        for (int i = 0; i < len; i++)
        {
            char ch = cstring.charAt(i);

            if (ch == '\\')
            {
                i++;
                ch = cstring.charAt(i);

                if (ch >= '0' && ch <= '7')
                {
                    val = 0;

                    for (int j = i; j - i < 3 && octalDigits.indexOf(ch = cstring.charAt(j)) != -1; j++)
                    {
                        val = val * 8 + (((int) ch) - '0');
                    }

                    ch = (char) val;
                    i += 3 - 1;
                }
                else if (ch == 'u')
                {
                    String temp = "";
                    for(int j = 1; j < 5; j++) {
                        temp += cstring.charAt(j+i);
                    }

                    int chint = Integer.parseInt(temp, 16);
                    char cc = new Character((char)chint).charValue();
                    ch = cc;
                    i += 4;

                    //Character

//                    i++;
//                    val = 0;
//
//                    for (int j = i; j - i < 4; j++)
//                    {
//                        ch = cstring.charAt(j);
//
//                        if (hexDigits.indexOf(ch) == -1)
//                        {
//                            //invalid
//                        }
//
//                        val *= 16;
//
//                        if (Character.isDigit(ch))
//                            val += (((int) ch) - '0');
//                        else if (Character.isLowerCase(ch))
//                            val += (((int) ch) - 'a');
//                        else
//                            val += (((int) ch) - 'A');
//                    }
//
//                    i += 4 - 1;
//                    ch = (char) val;
                }
                else if ((unesc = unescChars.indexOf(ch)) != -1)
                {
                    ch = escChars.charAt(unesc);
                }
                else
                {
                    //invalid char
                }
            }

            sb.append(ch); // usually have some translated character to append now
        }

        return sb.toString();
    }

    public static String escape( String raw )
    {
        if (raw == null)
            return raw;

        StringBuffer sb = new StringBuffer(raw.length() * 2);
        int unesc;
        int len;
        String hex;

        for (int i = 0; i < raw.length(); i++)
        {
            char ch = raw.charAt(i);
            int ich = (int) ch;

            if ((unesc = escChars.indexOf(ch)) != -1)
            {
                sb.append('\\');
                sb.append(unescChars.charAt(unesc));
            }
//            else if (ch < ' ' || ich >= 0x7f /*|| ich>0xff*/)
//            { // not printable or Unicode
//                sb.append("\\u");
//
//                hex = Integer.toHexString(ich);
//                len = hex.length();
//
//                for (int j = len; j < 4; j++)
//                    sb.append('0');
//
//                sb.append(hex);
//            }
            else
            {
                sb.append(ch);
            }
        }

        return sb.toString();
    }


    public static String getXSLTTemplateString(String s) {

        if (s.startsWith(XSLTSCHEME)) {
            int bi = XSLTSCHEME.length();
            int ei = s.length() ;
            String t1 = s.substring(bi, ei);
            String template = unescape(t1);
//            String template = unescapeXMLString(t1);
//            System.out.println(template);
            return template;
        }
        return s;

    }


    /**
     * First escapes '\' and '$' with '\' in the replacement String,
     * then returns <code>source.replaceAll(regex, replacement)</code>.
     * <p>See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4689750</p>
     * @param source A String.
     * @param regex The regular expression to which the source is to be matched.
     * @param replacement The String that will replace all matches.
     */
    public static String safeReplaceAll(String source, String regex, String replacement) {
        replacement = replacement.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
        return source.replaceAll(regex, replacement);
    }//escapeForReplaceAll

    public static String convertToValidTibcoIdentifier(String name, boolean replaceSlash) {
		String validIdentifier = name.replace('-', '_');
		validIdentifier = validIdentifier.replace(':', '_');
		validIdentifier = validIdentifier.replace('$', '_');
		validIdentifier = validIdentifier.replace('~', '_');
		validIdentifier = validIdentifier.replace('^', '_');
		validIdentifier = validIdentifier.replace('&', '_');
		validIdentifier = validIdentifier.replace(' ', '_');

		validIdentifier = validIdentifier.replace('.', '_');
		validIdentifier = (replaceSlash == true) ?
                validIdentifier.replace('/', '_') : validIdentifier;
		validIdentifier = validIdentifier.replace('\\', '_');
		validIdentifier = validIdentifier.replace(' ', '_');
        validIdentifier = validIdentifier.replace('"', '_');
        return validIdentifier;
	}

    /**
     * Inspects if string argument start with a invalid character
     * @param str
     * @return boolean true if string starts with a invalid designer character, false otherwise
     */
    public static boolean startsWithInValidTibcoIdentifier(String str) {
    	return str.startsWith("-") || str.startsWith(":")
				|| str.startsWith("$") || str.startsWith("~")
				|| str.startsWith("^") || str.startsWith("&")
				|| str.startsWith(" ") || str.startsWith(".")
				|| str.startsWith("/") || str.startsWith("\\")
				|| str.startsWith(" ") || str.startsWith("\"");
	}

    /**
     * Inspects if string argument ends with a invalid character
     * @param str
     * @return boolean true if string ends with a invalid designer character, false otherwise
     */
    public static boolean endsWithInValidTibcoIdentifier(String str) {
    	return str.endsWith("-") || str.endsWith(":")
				|| str.endsWith("$") || str.endsWith("~")
				|| str.endsWith("^") || str.endsWith("&")
				|| str.endsWith(" ") || str.endsWith(".")
				|| str.endsWith("/") || str.endsWith("\\")
				|| str.endsWith(" ") || str.endsWith("\"");
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "convertByteArrayToString",
        synopsis = "A faster way of converting a <code>byte[]</code> to <code>String</code>\nobject.\n<p>\nThis is especially useful when the buffer sizes are very large, where\n<code>String.getBytes(String)</code> is not optimal.\n</p>",
        signature = "String convertByteArrayToString(Object bytesObject, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bytesObject", type = "Object", desc = "The input byte[]"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The character set to use")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String convertByteArrayToString(Object bytesObject, String encoding) {
        if (!(bytesObject instanceof byte[])) {
            throw new IllegalArgumentException("Input should be a byte[]");
        }
        byte[] bytes = (byte[])bytesObject;
        CharsetDecoder decoder = Charset.forName(encoding).newDecoder();
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        CharBuffer cBuff = CharBuffer.allocate(bbuf.capacity());
        decoder.decode(bbuf, cBuff, false);
        //Flip the buffer, and take the position to 0.
        ((Buffer)cBuff).flip();
        String s = cBuff.toString();
        return s;
    }
}
