// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * XML character replacement utilities; find equivalent in XMLSDK (or add it), and delete this.
 */
public class CharacterEscapeUtils
{
    /**
     * If mapQuotesAndLtGt is false, does not handle '<', '>', '"', or "'", i.e. for use in xpath.  Move this fn elsewhere..
     */
    public static String escapeCharacters(String str, boolean isInAttribute, boolean mapQuotesAndLtGt)
    {
        return escapeCharacters(str,isInAttribute,mapQuotesAndLtGt,new HashSet(), new ArrayList());
    }

    /**
     * If mapQuotesAndLtGt is false, does not handle '<', '>', '"', or "'", i.e. for use in xpath.  Move this fn elsewhere..
     */
    public static String escapeCharacters(String str, boolean isInAttribute, boolean mapQuotesAndLtGt, Set usedCharacters, List usedCharactersInOrder) {
        int len = str.length();
        boolean hasEscape = false;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            String e = getEscapeString(c,mapQuotesAndLtGt,isInAttribute);
            if (e!=null) {
                hasEscape = true;
                break;
            }
        }
        if (!hasEscape) {
            return str;
        }
        StringBuffer sb = new StringBuffer(len+16);
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='\r' && i+1<len && str.charAt(i+1)=='\n') { // 1 special case for crlf
                Character cc = new Character('N'); // just a marker.
                if (!usedCharacters.contains(cc)) {
                    usedCharacters.add(cc);
                    usedCharactersInOrder.add(cc);
                }
                sb.append("&crlf;");
                i++;
            } else {
                String e = getEscapeString(c,mapQuotesAndLtGt,isInAttribute);
                if (e!=null) {
                    sb.append('&');
                    sb.append(e);
                    sb.append(';');
                    if (getBuiltInEscapeString(c,mapQuotesAndLtGt)==null) {
                        Character cc = new Character(c);
                        if (!usedCharacters.contains(cc)) {
                            usedCharacters.add(cc);
                            usedCharactersInOrder.add(cc);
                        }
                    }
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Searches the string for an unmatched '&' reference (i.e. for &amp; escaping).
     * @param str The string
     * @return The index of the first unmatched '&', or -1 for none.
     */
    static public int getFirstEscapeCharacterIssue(String str)
    {
        int len = str.length();
        //StringBuffer esc = null;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='&')
            {
                // 1 special case for crlf
                boolean found = false;
                int startAt = i;
                //if (esc==null) {
                //    esc = new StringBuffer();
                //}
                //esc.setLength(0);
                for (i++;i<len;i++)
                {
                    c = str.charAt(i);
                    if (c==';')
                    {
                        found = true;
                        break;
                    }
                    //esc.append(c);
                }
                if (!found)
                {
                    return startAt;
                }
            }
        }
        return -1; //ok.
    }

    public static String unescapeCharacters(String str) {
        int len = str.length();
        boolean hasEscape = false;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='&') {
                hasEscape = true;
                break;
            }
        }
        if (!hasEscape) {
            return str;
        }
        StringBuffer sb = new StringBuffer(len+16);
        StringBuffer esc = new StringBuffer();
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c=='&') { // 1 special case for crlf
                esc.setLength(0);
                int startAt = i;
                for (i++;i<len;i++) {
                    c = str.charAt(i);
                    if (c==';') {
                        break;
                    }
                    esc.append(c);
                }
                String entity = esc.toString();
                String ch = getEntityCharacter(entity);
                if (ch!=null)
                {
                    sb.append(ch);
                }
                else
                {
                    // bad escape, can't make this reversible, so just go ahead & assume it was supposed to be an ampersand by itself.
                    sb.append("&");
                    i = startAt;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Gets the character reference string for this entity, or null if not found.<br.
     * Mostly single character strings, except for CRLF.
     */
    private static String getEntityCharacter(String entity) {
        //WCETODO make these map based.
        if (entity.equals("crlf")) {
            return "\r\n";
        }
        if (entity.equals("lf")) {
            return "\n";
        }
        if (entity.equals("cr")) {
            return "\r";
        }
        if (entity.equals("tab")) {
            return "\t";
        }
        if (entity.equals("amp")) {
            return "&";
        }
        if (entity.equals("quot")) {
            return "\"";
        }
        if (entity.equals("lt")) {
            return "<";
        }
        if (entity.equals("gt")) {
            return ">";
        }
        if (entity.equals("nbsp")) {
            return "" + (char)160;
        }
        return null;
    }

    private static String getEscapeString(char c, boolean mapQuotesAndLtGt, boolean isInAttribute) {
        String b = getBuiltInEscapeString(c,mapQuotesAndLtGt);
        if (b!=null) {
            return b;
        }
        switch (c) {
            case '\t': return "tab";
            case '\r': return "cr";
            case '\n': return isInAttribute ? "lf" : null;
            case (char)160: return "nbsp";
        }
        return null;
    }

    private static String getBuiltInEscapeString(char c, boolean mapQuotes) {
        switch (c) {
            case '&': return "amp";
            case '<': if (mapQuotes) return "lt";
            case '>': if (mapQuotes) return "gt";
            case '"': if (mapQuotes) return "quot"; break;
            case '\'': if (mapQuotes) return "apos"; break;
        }
        return null;
    }

}

