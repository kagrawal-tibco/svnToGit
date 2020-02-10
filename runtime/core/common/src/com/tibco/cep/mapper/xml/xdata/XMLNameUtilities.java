package com.tibco.cep.mapper.xml.xdata;

import java.util.Set;

import com.tibco.io.xml.XMLStringUtilities;

/**
 * Contains some methods used to create XML-compliant element names
 */
public class XMLNameUtilities {

    /**
     * Checks if the provided character is a valid name (NCName) start chaacter
     * @deprecated Use {@link XMLStringUtilities}.
     */
    public static boolean isNameStartChar(char c){
	return (c >= 'A' && c <= 'Z')
        || (c >= 'a' && c <= 'z')
        || c == '_'
        // No --- this fn is for NCname, not QName... || c==':'
        || (c >= 0xC0 && c <= 0xD6) // Euro-letters.
        || (c >= 0xD8 && c <= 0xF6) // More Euro-letters.
        || (c >= 0xF8 && c <= 0xFF) // ... and more euro-letters.
        || c > 0xFF;
    }

    /**
     * Checks if the provided character is a valid name (NCName) chaacter
     * @deprecated Use {@link XMLStringUtilities}.
     */
    public static boolean isNameChar(char c, boolean isNamespace) {
        return isNameStartChar(c)
            || (c >= '0' && c <= '9')
            || c == '-'
            || c == '.'
            || (isNamespace && c == '/')
            || (isNamespace && c == ':');
    }

    public static boolean isNameChar(char c) {
        return isNameChar(c, false);
    }

    /** Does this look like a valid name? (NCName)
     * @deprecated Use {@link com.tibco.io.xml.XMLStringUtilities#isNCName} or {@link com.tibco.io.xml.XMLStringUtilities#isQName}
     */
    public static boolean isValidName(String name, boolean isNamespace) {
        if (name.length() == 0)
            return false;
        if (!isNameStartChar(name.charAt(0)))
            return false;
        for (int i = 1; i < name.length(); i++){
            char c = name.charAt(i);
            if (!isNameChar(name.charAt(i), isNamespace))
                return false;
        }
        return true;
    }

    /** Does this look like a valid name? (NCName)
     * @deprecated Use {@link com.tibco.io.xml.XMLStringUtilities#isNCName}
     */
    public static boolean isValidName(String name) {
        return XMLStringUtilities.isNCName(name);
    }

    /** ensures that name is a valid XML name and makes it so if it is not.
    *	It does this by stripping out non valid chars. If the results is an
    *	empty string, we try again with a '_' prefix.
    * @deprecated Use {@link com.tibco.xml.util.NameMangler}.
    */
    public static String makeValidName(String name, boolean isNamespace){
        StringBuffer sb = new StringBuffer();
        boolean firstFound = false;
        for (int i = 0; i < name.length(); i++){
            if (firstFound == false) {
                if (isNameStartChar(name.charAt(i)) ){
                    sb.append(name.charAt(i));
                    firstFound = true;
                }
            }
            else {
                if (isNameChar(name.charAt(i), isNamespace) ) {
                    sb.append(name.charAt(i));
                }
            }
        }
        if (sb.length() == 0) {
            return makeValidName("_"+name);
        }
        return sb.toString();
    }

    /**
     * @deprecated Use {@link com.tibco.xml.util.NameMangler}.
     */
    public static String makeValidName(String name) {
        return makeValidName(name, false);
    }

    /** ensures that name is a valid XML name and makes it so if it is not.
     *  Because the algorithm for 'fixing' names doesn't necessarily guarantee
     *  uniqueness (i.e. 'a^b' and 'a&b' both become 'ab'), this method also
     *  takes a Set of already used names (Strings) and guarantees uniqueness
     *  against that set by appending a number in the case of a collision.<BR>
      * @deprecated Use {@link com.tibco.xml.util.NameMangler}.
    */
    public static String makeValidName(String name, Set alreadyUsedNames, boolean isNamespace) {
        for (int i=1;;i++) {
            String tryName;
            if (i==1) {
                tryName = name;
            } else {
                tryName = name + i;
            }
            String got = makeValidName(tryName, isNamespace);
            if (!alreadyUsedNames.contains(got)) {
                return got;
            }
        }
    }

    public static String makeValidName(String name, Set alreadyUsedNames) {
        return makeValidName(name, alreadyUsedNames, false);
    }

    /**
     * Encode a string using entity references, i.e. &amp; &quot;
     * (This code must exist somewhere else...)
     */
    public static String xmlStringFormat(String str) {
        int len = str.length();
        // First check to see if there are any substitution characters:
        boolean found = false;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (getEntityRef(c)!=null) {
                found = true;
                break;
            }
        }
        if (!found) return str;
        // There was some substitution:
        StringBuffer sb = new StringBuffer(len+10);
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            String r = getEntityRef(c);
            if (r==null) {
                sb.append(c);
            } else {
                sb.append('&');
                sb.append(r);
                sb.append(';');
            }
        }
        return sb.toString();
    }

    /**
     * Same as xmlStringFormat, but encodes escapes the extra whitespace characters tab & new-line:
     */
    public static String xmlStringFormatForData(String str) {
        int len = str.length();
        // First check to see if there are any substitution characters:
        boolean found = false;
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (getEntityRefForData(c)!=null) {
                found = true;
                break;
            }
        }
        if (!found) return str;
        // There was some substitution:
        StringBuffer sb = new StringBuffer(len+10);
        for (int i=0;i<len;i++) {
            char c = str.charAt(i);
            String r = getEntityRefForData(c);
            if (r==null) {
                sb.append(c);
            } else {
                sb.append('&');
                sb.append(r);
                sb.append(';');
            }
        }
        return sb.toString();
    }

    private static String getEntityRef(int ch)
    {
        // (Need to fix this to handle all non-xml characters as escapes...)

        // Encode special XML characters into the equivalent character references.
        // These five are defined by default for all XML documents.
        // Also, have ones for non-xml characters:
        switch ( ch ) {
            case '\r': // Must write this out because o.w. it will get normalized to ' ' on the way in.
                return "#13";
            case '\u00A0': // this character doesn't go out well otherwise.
                return "#160";
            case '<':
                return "lt";
            case '>':
                return "gt";
            case '"':
                return "quot";
            case '\'':
                return "apos";
            case '&':
                return "amp";
        }
        return null;
    }

    private static String getEntityRefForData(int ch)
    {
        // (Need to fix this to handle all non-xml characters as escapes...)

        // Encode special XML characters into the equivalent character references.
        // These five are defined by default for all XML documents.
        // Also, have ones for non-xml characters:
        switch ( ch ) {
            case '\r': // Must write this out because o.w. it will get normalized to '\n' on the way in.
                return "#13";
            case '\n': // Must write this out because o.w. it will get normalized to ' ' on the way in (in attributes, do the same for elements)
                return "#10";
            case '\t': // Must write this out because o.w. it will get normalized to ' ' on the way in (in attributes, do the same for elements)
                return "#9";
            case '\u00A0': // this character doesn't go out well otherwise.
                return "#160";
            case '<':
                return "lt";
            case '>':
                return "gt";
            case '"':
                return "quot";
            case '\'':
                return "apos";
            case '&':
                return "amp";
        }
        return null;
    }
}

