package com.tibco.cep.query.exec.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 22, 2008
 * Time: 6:05:13 PM
 */

/**
 * Escapes data, usually for inclusing into generated code.
 */
public class Escaper {


    protected static final Pattern PATTERN_JAVA_STRING_SPECIAL_CHAR =
            Pattern.compile("(\\\\|\\t|\\r|\"|\\n|[^\\p{Print}])", Pattern.MULTILINE);


    public static String toJavaStringSource(String value) {
        return toJavaStringSource(value, "null");
    }


    public static String toJavaStringSource(String value, String defaultSource) {
        if (null == value) {
            return defaultSource;
        } else {
            final Matcher m = PATTERN_JAVA_STRING_SPECIAL_CHAR.matcher(value);
            final StringBuffer sb = new StringBuffer("\"");
            while (m.find()) {
                final char c = m.group(0).charAt(0);
                switch (c) {
                    case '\\': m.appendReplacement(sb, "\\\\\\\\"); break;
                    case '\t': m.appendReplacement(sb, "\\\\t"); break;
                    case '\r': m.appendReplacement(sb, "\\\\r"); break;
                    case '\n': m.appendReplacement(sb, "\\\\n"); break;
                    case '"': m.appendReplacement(sb, "\\\\\""); break;
                    default: {
                        // Workaround (see below)
                        m.appendReplacement(sb, "\" + ((char) " + ((int) c) + ") + \"");


// Javassist bug causes loss of \
//                        final String code = Integer.toString(c, 16);
//                        final StringBuffer sbChar = new StringBuffer("\\\\u");
//                        for (int i = code.length(); i < 4; i++) {
//                            sbChar.append('0');
//                        }
//                        sbChar.append(code);
//                        m.appendReplacement(sb, sbChar.toString());
                    }
                }
            }
            m.appendTail(sb);
            return sb.append('"').toString();
        }//else
    }


    public static String toJavaCharSource(Character c) {
        return toJavaCharSource(c, "null");
    }


    public static String toJavaCharSource(Character c, String defaultSource) {
        if (null == c) {
            return defaultSource;
        } else if (c.equals('\\') || c.equals('\'') || Character.isISOControl(c)) {
            final String code = Integer.toString(c, 16);
            final StringBuffer sb = new StringBuffer("'\\u");
            for (int i = code.length(); i < 4; i++) {
                sb.append('0');
            }
            return sb.append(code).append('\'').toString();
        } else {
            return "'" + c + "'";
        }//else
    }



}
