/**
 * @author ishaan
 * @version Jun 22, 2004, 3:51:45 PM
 */
package com.tibco.cep.studio.common.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.HashSet;

import com.tibco.cep.designtime.model.Folder;

/**
 * A class that contains methods for manipulating Strings which may not be valid Entity names.
 */
public class EntityNameHelper {
	
	static CharsetEncoder asciiEncoder = 
		Charset.forName("ASCII").newEncoder(); // or "ISO-8859-1" for ISO Latin 1

    protected static final char FIRST_CHARACTER_FOR_ENTITY_CONVERSION = '_';

    private EntityNameHelper() {}
    public static final HashSet KEYWORDS = new HashSet(Arrays.asList(new String[] {
        //keywords for .rul files
        //todo remove these when .rul files aren't used
        "rule",
        "process",
        "attribute",
        "declare",
        "when",
        "then",
        "requeue",
        "priority",
        
        //new ones
        "key",
        "lock",
        "body",
        "forwardChain",
        "backwardChain",
        "rank",
        "validity",
        "alias",
        "virtual",
        "scope",
        "id", // new for 5.1
        "extId", // new for 5.1
        
        //unimplemeted keywords
        "this",
        "moveto",
        
        //implemented keywords
        "true",
        "false", 
        "null",
        "if",
        "else",
        "while",
        "continue",
        "break",
        "return",
        
        //these type names are part of the RDFTypes
        //and therefore don't need to be given
        //their own tokens in the language, but
        //since they are still special tokens in java
        //they need to be disallowed as entity names,
        //and keeping them as implemented keywords rather
        //than java keywords prevents the highliter from
        //turning them red
        "int",
        "long",
        "double",
        "boolean",
        "Concept",
        "ContainedConcept",
        "Event",
        "SimpleEvent",
        "TimeEvent",
       
        //new one
        "AdvisoryEvent",

        //java keywords (unimplemented)
        "package",
        "char",
        "float",
        "abstract",
        "default",
        "private",
        "do",
        "implements",
        "protected",
        "throw",
        "import",
        "public",
        "throws",
        "byte",
        "instanceof",
        "transient",
        "case",
        "extends",
        "short",
        "try",
        "catch",
        "final",
        "interface",
        "static",
        "void",
        "finally",
        "strictfp",
        "volatile",
        "class",
        "native",
        "super",
        "const",
        "for",
        "new",
        "switch",
        "goto",
        "synchronized",
  
        //new ones
        "enum",
        "assert",
        "String",
        
        //query
        "and",
        "or",
        "in",
        "distinct",
        "from",
        "select",
        "as",
        "where",
        "group",
        "by",
        "having",
        "order",
        "asc",
        "desc",
        "all",
        "exists",
        "like",
        "between",
        "union",
        "except",
        "mod",
        "abs",
        "not",
        "first",
        "last",
        "unique",
        "sum",
        "min",
        "max",
        "avg",
        "count",
        "is_undefined",
        "is_defined",
        "DateTime",
        "date",
        "time",
        "timestamp",
        
        //pattern
        "define",
        "pattern",
        "using",
        "event",
        "such",
        "that",
        "starts",
        "with",
        "any",
        "one",
        "repeat",
        "within",
        "during",
        "to",
        "times",
        "after",
        "day",
        "days",
        "hour",
        "hours",
        "minute",
        "minutes",
        "second",
        "seconds",
        "millisecond",
        "milliseconds",
    }));

    private static final HashSet PROPERTY_ATTRIBUTES = new HashSet(Arrays.asList(new String[] {
        "_id_", "_extId_", "_nm_", "_ns_", "_payload_"
    }));

    public static boolean isAttributeName(String str) {
        return PROPERTY_ATTRIBUTES.contains(str);
    }

    /**
     * Returns true if the provided String is a keyword.
     * @param str The String to test.
     * @return true if the provided String is part of the keyword array KEYWORDS.
     */
    public static boolean isKeyword(String str) {
    	return KEYWORDS.contains(str);
    }

    /**
     * Replaces non-identifier characters within the provided String with the specified character.  The returned String is guaranteed to be a valid identifer.
     * @param str The String in which to replace non-identifier characters.
     * @param replaceWithChar The character to replace non-identifier characters with.  If it is not an identifier character, '_' (underscore) will be used.
     * @param coalesce If true, consecutive non-identifier characters will be replaced with a single char.
     * @param removeLeading If true, leading non-identifier characters will be removed rather than replaced.  If necessary, a '_' will be prepended to ensure a valid identifier is returned.
     * @param removeTrailing If true, trailing non-identifier characters will be removed rather than replaced.
     * @return A String, based on str, with all non-identifier characters replaced with replaceWithChar.
     */
    public static String replaceNonIdentifierChars(String str, char replaceWithChar, boolean coalesce, boolean removeLeading, boolean removeTrailing) {
        if(str == null || str.length() == 0) return "_";
        if(!ModelNameUtil.isIdentifierPart(replaceWithChar)) replaceWithChar = '_';

        StringBuffer sb = new StringBuffer(str);
        
        for(int i = 0; i < sb.length(); i++) {
            char ch = sb.charAt(i);
            if(!ModelNameUtil.isIdentifierPart(ch)) sb.setCharAt(i, replaceWithChar);
        }

        str = sb.toString();
        if(coalesce) str = str.replaceAll("(" + replaceWithChar + ")+", String.valueOf(replaceWithChar));
        if(removeLeading && (str.length() > 0) && (str.charAt(0) == replaceWithChar)) str = str.substring(1, str.length());
        if(removeTrailing && (str.length() > 0) && (str.charAt(str.length() - 1) == replaceWithChar)) str = str.substring(0, str.length() - 1);

        if(str.length() == 0 || !ModelNameUtil.isIdentifierStart(str.charAt(0))) str = '_' + str;

        return str;
    }

//    public static void main(String args[]) {
//        String before, after;
//
//        before = "_Concept (1)_";
//        after = replaceNonIdentifierChars(before, '_', true, true, true);
//        System.out.println("Before: " + before);
//        System.out.println("After: " + after);
//
//        after = replaceNonIdentifierChars(before, '_', true, true, false);
//        System.out.println("\nBefore: " + before);
//        System.out.println("After: " + after);
//
//        before = "_";
//
//        after = replaceNonIdentifierChars(before, '_', false, true, true);
//        System.out.println("\nBefore: " + before);
//        System.out.println("After: " + after);
//
//        after = replaceNonIdentifierChars(before, '_', true, false, true);
//        System.out.println("\nBefore: " + before);
//        System.out.println("After: " + after);
//
//        before = "";
//
//        after = replaceNonIdentifierChars(before, '_', true, true, true);
//        System.out.println("\nBefore: " + before);
//        System.out.println("After: " + after);
//        
//        before = "%Concept (1)_";
//        after = replaceNonIdentifierChars(before, '_', true, true, true);
//        System.out.println("Before: " + before);
//        System.out.println("After: " + after);
//        
//        before = "0Concept (1)_";
//        after = replaceNonIdentifierChars(before, '_', true, true, true);
//        System.out.println("Before: " + before);
//        System.out.println("After: " + after);
//
//        String path = "/Objects/Concepts/Concept_1";
//        boolean isValid = isValidEntityPath(path, true);
//        System.out.println("The path " + path + " is" + (isValid ? "" : " not") +  " valid");
//
//    }

    /**
     * Returns true if the specified String starts with the Folder separator character and each
     * String between occurrences of the Folder separator is a valid entity identifier.
     * @param string The String to test.
     * @return true if and only if Character.isJavaIdentifierStart(string.charAt(0)) returns true and Character.isJavaIdentifierPart() returns true for all characters of string.
     */

    public static boolean isValidEntityPath(String string, boolean allowKeywords) {
        if(!string.startsWith(("" + Folder.FOLDER_SEPARATOR_CHAR))) return false;

        String[] entities = string.split("" + Folder.FOLDER_SEPARATOR_CHAR);
        for(int i = 0; i < entities.length; i++) {
            if(entities[i].length() == 0) continue;
            if(!isValidEntityIdentifier(entities[i])) return false;
            if(!allowKeywords && isKeyword(entities[i])) return false;
        }

        return true;
    }

    /**
     * Returns true if the passed in String is a valid Entity identifier.
     * @param string
     * @return
     */
    public static boolean isValidEntityIdentifier(String string) {
        if(string.length() == 0) return true;
        return ModelNameUtil.isValidIdentifier(string);
    }

    public static boolean containsInvalidNameChars(String name, boolean allowPathSeparator) {
        return containsInvalidNameChars(name, allowPathSeparator, false);
    }

    /**
     * Returns true if the passed in String contains invalid Entity name characters.
     * @param name The String to check.
     * @param allowPathSeparator Whether or not to allow the path separator character.
     * @param allowSpaces Whether or not to allow spaces.
     * @return true, if the name contains any invalid characters.
     */
    public static boolean containsInvalidNameChars(String name, boolean allowPathSeparator, boolean allowSpaces) {
        if ((null == name) || (0 == name.trim().length())) {
            return false;
        }//if

        for(int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);

            if(ch == '_' && allowSpaces) continue;

            boolean invalidSep = (ch == Folder.FOLDER_SEPARATOR_CHAR) && !allowPathSeparator;
            boolean invalidPart = (ch != Folder.FOLDER_SEPARATOR_CHAR) && (!ModelNameUtil.isIdentifierPart(ch));

            if(invalidSep || invalidPart) return true;
        }

        return false;
    }




    /**
     * Makes a best attempt at converting a name into a valid EntityIdentifier with a minimum amount of modifications.
     * There is no guarantee that the name returned is not already in use.
     * This method is a function, i.e. it always returns the same value when provided with the same input.
     * @param name The String name to convert.
     * @return A String that contains a valid EntityIdentifier.
     */
    public static String convertToEntityIdentifier(String name) {
        if ((null == name) || (name.length() < 1)) {
            return FIRST_CHARACTER_FOR_ENTITY_CONVERSION + "";
        }//if
        name = replaceNonIdentifierChars(name, '_',  false, false, false);
        if (!ModelNameUtil.isIdentifierStart(name.charAt(0))) {
            return FIRST_CHARACTER_FOR_ENTITY_CONVERSION + name;
        }
        return name;
    }//convertToEntityIdentifier
    
    /**
     * @param string
     * @return
     */
    public static boolean isValidBEEntityIdentifier(String string) {
    	if(isKeyword(string)) return false;
    	return ModelNameUtil.isValidIdentifier(string);
    }
    
    /**
     * @param string
     * @return
     */
    public static boolean isValidSharedResourceIdentifier(String string) {
    	if(isKeyword(string)) return false;
    	return ModelNameUtil.isValidSharedResourceIdentifier(string);
    }
    /**
     * @param string
     * @return
     */
    public static boolean isValidBEProjectIdentifier(String string) {
    	if(isKeyword(string)) return false;
    	if(!isPureAscii(string)) return false;
    	return ModelNameUtil.isValidProjectIdentifier(string);
    }
    
	public static boolean isPureAscii(String v) {
		return asciiEncoder.canEncode(v);
	}

}//class
