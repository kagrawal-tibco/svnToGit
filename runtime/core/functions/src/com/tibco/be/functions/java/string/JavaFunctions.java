package com.tibco.be.functions.java.string;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;

import com.tibco.be.util.BEStringUtilities;

import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * User: apuneet
 * Date: Aug 24, 2004
 * Time: 10:09:10 AM
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "String",
        synopsis = "Utility Functions to Operate on String and Char Properties")


public final class JavaFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "charAt",
        synopsis = "Returns the character at the specified index.",
        enabled = @Enabled(value=false),
        signature = "char charAt (String s, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s", type = "String", desc = "The String that contains the character."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of the character in the String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "char", desc = "at  <code>index</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the character at the specified index. An index ranges from 0 to\nlength() - 1. The first character of the sequence is at index 0, the\nnext at index 1, and so on, as for array indexing.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static char charAt(String s, int index) {
        if(s == null) throw new RuntimeException("Argument s for charAt() is null");
        return s.charAt(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "compareTo",
        synopsis = "Compares two strings lexicographically.",
        signature = "int compareTo (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to compare."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A second String to compare.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "A negative integer, zero, or a positive integer as s1 is\ngreater than, equal to, or less than s2."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compares two strings lexicographically. The comparison is based on the\nUnicode value of each character in the strings. The character sequence\nrepresented by this String object is compared lexicographically to the\ncharacter sequence represented by the argument string. The result is a\nnegative integer if the String <code>s1</code> lexicographically precedes the\nString <code>s2</code>. The result is a positive integer if the String <code>s1</code>\nlexicographically follows the string <code>s2</code>. The result is zero if the\nstrings are equal; compareTo returns 0 exactly when the equals (Object)\nmethod would return true.\nThis is the definition of lexicographic ordering. If two strings are\ndifferent, then either they have different characters at some index that\nis a valid index for both strings, or their lengths are different, or\nboth. If they have different characters at one or more index positions,\nlet k be the smallest such index; then the string whose character at\nposition k has the smaller value, as determined by using the < operator,\nlexicographically precedes the other string. In this case, compareTo\nreturns the difference of the two character values at position k in the\ntwo string -- that is, the value:\ns1.charAt (k) - s2.charAt (k)\nIf there is no index position at which they differ, then the shorter\nstring lexicographically precedes the longer string. In this case,\ncompareTo returns the difference of the lengths of the strings -- that is, the value:\ns1.length () - s2.length ()",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int compareTo(String s1, String s2) {
        if(s1 == null) {
            if (s2 == null) return 0;
            else return -1 * s2.length();
        }
        else {
            if (s2 == null) return s1.length();
            else return s1.compareTo(s2);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "compareToIgnoreCase",
        synopsis = "Compares two strings lexicographically, ignoring case differences.",
        signature = "int compareToIgnoreCase (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to compare."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A second String to compare.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "a negative integer, zero, or a positive integer as s1 is\ngreater than, equal to, or less than s2 ignoring case considerations."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compares two strings lexicographically, ignoring case differences.\nThis method returns an integer whose sign is that of calling compareTo\nwith normalized versions of the strings where case differences have been\neliminated by lowercasing each character.\nNote that this method does not take locale into account, and will result\nin an unsatisfactory ordering for certain locales.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int compareToIgnoreCase(String s1, String s2) {
        if(s1 == null) {
            if (s2 == null) return 0;
            else return -1 * s2.length();
        }
        else {
            if (s2 == null) return s1.length();
            else return s1.compareToIgnoreCase(s2);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "equals",
        synopsis = "Compares two Strings and returns a boolean indicating whether they are equal.",
        signature = "boolean equals (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to compare."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A second String to compare.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the Strings are equal, otherwise returns false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compares two Strings and returns a boolean indicating whether they are equal.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean equals(String s1, String s2) {
        if(s1 == null) {
            if (s2 == null) return true;
            else return false;
        }
        else {
            return s1.equals(s2);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "endsWith",
        synopsis = "Tests if the String <code>s1</code> ends with the specified suffix.",
        signature = "boolean endsWith (String s1, String suffix)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test for the presence of the suffix."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "suffix", type = "String", desc = "A suffix String to test for in the String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>s1</code> ends with <code>suffix</code>, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tests if the String <code>s1</code> ends with the specified suffix.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean endsWith(String s1, String suffix) {
        if(s1 == null) return false;
        return s1.endsWith(suffix);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "startsWith",
        synopsis = "Tests if the String <code>s1</code> ends with the specified prefix.",
        signature = "boolean startsWith (String s1, String prefix)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test for the presence of the prefix."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "prefix", type = "String", desc = "A prefix String to test for in the String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>s1</code> starts with <code>prefix</code>, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tests if the String <code>s1</code> starts with the specified prefix.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean startsWith(String s1, String prefix) {
        if(s1 == null) return false;
        return s1.startsWith(prefix);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "length",
        synopsis = "Returns the length of the String passed in.",
        signature = "int length (String s1)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to find the length of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The length of the String passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the length (number of characters) of the String passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int length(String s1) {
        if(s1 == null) return 0;
        return s1.length();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfChar",
        synopsis = "Returns the index within this string of the first occurrence\nof the specified character, starting the search at the specified index.",
        enabled = @Enabled(value=false),
        signature = "int indexOfChar (String s1, int fromIndex, char ch)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to search for the char <code>ch</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromIndex", type = "int", desc = "The index within <code>s1</code> to begin the search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ch", type = "char", desc = "The char to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index within this string of the first occurrence of the\nspecified character, or -1 if ch is not present."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index within this string of the first occurrence of the\nspecified character, starting the search at the specified index.  If\na character with value ch occurs in the character sequence represented\nby this String object at an index no smaller than fromIndex, then the\nindex of the first such occurrence is returned.  If no such character\noccurs in this string at or after position fromIndex, then -1 is\nreturned.  There is no restriction on the value of fromIndex. If it is\nnegative, it has the same effect as if it were zero: this entire string\nmay be searched. If it is greater than the length of this string, it has\nthe same effect as if it were equal to the length of this string: -1 is\nreturned.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int indexOfChar(String s1, int fromIndex, char ch) {
        if(s1 == null) return -1;
        return s1.indexOf(ch,fromIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfString",
        synopsis = "Finds the index of the first occurrence of <code>s2</code> within <code>s1</code> " +
                "that is at, or after, <code>fromIndex</code>.",
        signature = "int indexOfString (String s1, int fromIndex, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromIndex", type = "int", desc = "A position within <code>s1</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A String to find within <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int",  desc = "Returns the index of the first occurrence of <code>s2</code> within <code>s1</code> " +
                "that is at, or after, <code>fromIndex</code>, if there is one. Else returns <code>-1</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds the index of the first occurrence of <code>s2</code> within <code>s1</code> " +
                "that is at, or after, <code>fromIndex</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "int  result = String.indexOfString(\"This is a test. This is a test.\", 3, \"is \");<br/><br />\n" +
                "Result is: 5"
    )
    public static int indexOfString(String s1, int fromIndex, String s2) {
        if(s1 == null) return -1;
        if(fromIndex > s1.length()) fromIndex = s1.length();
        if(fromIndex < 0) fromIndex = 0;
        return s1.substring(fromIndex).indexOf(s2);
    }



    @com.tibco.be.model.functions.BEFunction(
        name = "lastIndexOfChar",
        synopsis = "Returns the index within the String <code>s1</code> of the\nlast occurrence of the specified character starting at a specified\nposition in the String.",
        enabled = @Enabled(value=false),
        signature = "int lastIndexOfChar (String s1, int fromIndex, char ch)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromIndex", type = "int", desc = "The index within <code>s1</code> to begin the search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ch", type = "char", desc = "The char to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index within the String <code>s1</code> of the\nlast occurrence of the specified character starting at a specified\nposition in the String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index within the String <code>s1</code> of the\nlast occurrence of the specified character starting at a specified\nposition in the String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int lastIndexOfChar(String s1, int fromIndex, char ch) {
        if(s1 == null) return -1;
        return s1.lastIndexOf(ch,fromIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "lastIndexOfString",
        synopsis = "Finds the index of the last occurrence of <code>s2</code> within <code>s1</code> " +
                "that is at, or after, <code>fromIndex</code>.",
        signature = "int lastIndexOfString (String s1, int fromIndex, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fromIndex", type = "int",
                    desc = "A position within <code>s1</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String",
                    desc = "A String to find within <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index within the String <code>s1</code> of the\nlast occurrence of the String <code>s2</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Finds the index of the last occurrence of <code>s2</code> within <code>s1</code> " +
                "that is at, or after, <code>fromIndex</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "int  result = String.lastIndexOfString(\"This is a test. This is a test.\", 3, \"is \");<br/><br />\n" +
                "Result is: 21"
    )
    public static int lastIndexOfString(String s1, int fromIndex, String s2) {
        if(s1 == null) return -1;
        if(fromIndex > s1.length()) fromIndex = s1.length();
        if(fromIndex < 0) fromIndex = 0;
        return s1.substring(fromIndex).lastIndexOf(s2);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "matches",
        synopsis = "Tells whether or not this string matches the given regular expression.",
        signature = "boolean matches (String s1, String regex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to search."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "regex", type = "String", desc = "<code>s1</code> is to be matched.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the String <code>s1</code> matches the regular\nexpression <code>regex</code>, otherwise returns false."),
        version = "1.0",
        see = "java.util.regex.Pattern",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tells whether or not this string matches the given regular expression.\nTo see a description of the regular expression syntax see the\njava.util.regex.Pattern documentation.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean matches(String s1, String regex) {
        if(s1 == null) return false;
        return s1.matches(regex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "replace",
        synopsis = "Returns a new string resulting from replacing all occurrences\nof oldChar in this string with newChar.",
        signature = "String replace (String s1, char oldChar, char newChar)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to replace characters in."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "oldChar", type = "char", desc = "The old char to replace in the String <code>s1</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "newChar", type = "char", desc = "The new char to replace the old char with, in the String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A string derived from the String <code>s1</code> by\nreplacing every occurrence of oldChar with newChar."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a new string resulting from replacing all occurrences of oldChar\nin this string with newChar.  If the character oldChar does not occur\nin the character sequence represented by this String object, then a\nreference to this String object is returned. Otherwise, a new String\nobject is created that represents a character sequence identical to the\ncharacter sequence represented by this String object, except that every\noccurrence of oldChar is replaced by an occurrence of newChar.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String replace(String s1, char oldChar, char newChar) {
        if(s1 == null) return null;
        return s1.replace(oldChar,newChar);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "replaceAll",
        synopsis = "Replaces each substring of this string that matches the given\nregular expression with the given replacement.",
        signature = "String replaceAll (String s1, String regex, String replacement)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to replace characters in."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "regex", type = "String", desc = "<code>s1</code> is to be matched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "replacement", type = "String", desc = "A String to replace regular expression matches with.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A string derived from the String <code>s1</code> by\nreplacing every occurrence of the regular expression with <code>replacement</code>."),
        version = "1.0",
        see = "java.util.regex.Pattern",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Replaces each substring of this string that matches the given\nregular expression with the given replacement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String replaceAll (String s1, String regex, String replacement) {
        if(s1 == null) return null;
        return s1.replaceAll(regex,replacement);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "substring",
        synopsis = "Returns a new string that is a substring of this string.",
        signature = "String substring (String s1, int beginIndex, int endIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to use as a source of the substring."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "beginIndex", type = "int", desc = "The String index where the substring should start."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endIndex", type = "int", desc = "The String index where the substring should end.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A new string that is a substring of this string starting\nat <code>beginIndex</code> and continuing to <code>endIndex</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a new string that is a substring of this string. The substring\nbegins at the specified beginIndex and extends to the character at index\n<code>endIndex - 1</code>. Thus the length of the substring is\n<code>endIndex - beginIndex</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "int  result = String.substring(\"This is a test. This is a test.\", 5, 9);<br/><br />\n" +
                "Result is: \"is a\""
    )
    public static String substring(String s1, int beginIndex, int endIndex) {
        if(s1 == null) return null;
        return s1.substring(beginIndex, endIndex);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "replaceFirst",
        synopsis = "Replaces the first substring of this string that matches the\ngiven regular expression with the given replacement.",
        signature = "String replaceFirst (String s1, String regex, String replacement)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to replace characters in."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "regex", type = "String", desc = "<code>s1</code> is to be matched."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "replacement", type = "String", desc = "with.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A string derived from the String <code>s1</code> by\nreplacing every occurrence of the regular expression with <code>replacement</code>."),
        version = "1.0",
        see = "java.util.regex.Pattern",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Replaces the first substring of this string that matches the\ngiven regular expression with the given replacement.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String replaceFirst(String s1, String regex, String replacement) {
        if(s1 == null) return null;
        return s1.replaceFirst(regex,replacement);
    }



    @com.tibco.be.model.functions.BEFunction(
        name = "regionMatches",
        synopsis = "Tests if two string regions are equal.",
        signature = "boolean regionMatches (boolean ignoreCase, String s1, int offset1, String s2, int offset2, int length)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ignoreCase", type = "boolean", desc = "if true, ignore case when comparing characters."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test for region matches."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "offset1", type = "int", desc = "The starting offset of the subregion in the String <code>s1</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "Second String to test for region matches."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "offset2", type = "int", desc = "The starting offset of the subregion in the string <code>s2</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "The number of characters to compare.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the specified subregion of string s1 matches\nthe specified subregion of the string s2; false otherwise. Whether\nthe matching is exact or case insensitive depends on the ignoreCase\nargument."),
        version = "1.0",
        see = "java.lang.String#regionMatches",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tests if two identified string regions are equal.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean regionMatches (boolean ignoreCase, String s1,int offset1, String s2, int offset2, int length) {
        if(s1 != null && s2 != null)
            return s1.regionMatches(ignoreCase, offset1, s2, offset2, length);
        else
            return false;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toUpperCase",
        synopsis = "Converts all of the characters in the String passed to upper case\nusing the rules of the default locale.",
        signature = "String toUpperCase (String s1)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to convert to upper case.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String identical to the String passed in except that all\nchars have been converted to upper case."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Converts all of the characters in the String passed to upper case\nusing the rules of the default locale.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String toUpperCase(String s1) {
        if(s1 == null) return null;
        return s1.toUpperCase();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toLowerCase",
        synopsis = "Converts all of the characters in the String passed to lower case\nusing the rules of the default locale.",
        signature = "String toLowerCase (String s1)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to convert to lower case.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String identical to the String passed in except that all\nchars have been converted to lower case."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Converts all of the characters in the String passed to lower case\nusing the rules of the default locale.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String toLowerCase(String s1) {
        if(s1 == null) return null;
        return s1.toLowerCase();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "concat",
        synopsis = "Concatenates the two argument strings and returns the resulting String.",
        signature = "String concat (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to concatenate."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A second String to concatenate.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result of concatenating the two argument strings."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Concatenates the two argument strings and returns the resulting String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String concat(String s1, String s2) {
        if(s1 == null) return s2;
        if(s2 == null) return s1;     //for SR 1-6NYP3G
        return s1.concat(s2);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfInt",
        synopsis = "Returns a String representation of the int passed in.",
        signature = "String valueOfInt (int i)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "i", type = "int", desc = "An int to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the int passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the int passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String valueOfInt(int i) {
        return String.valueOf(i);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfLong",
        synopsis = "Returns a String representation of the long passed in.",
        signature = "String valueOfLong (long l)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "l", type = "long", desc = "A long to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the long passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the long passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String valueOfLong(long l) {
        return String.valueOf(l);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfDouble",
        synopsis = "Returns a String representation of the double passed in.",
        signature = "String valueOfDouble (double d)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d", type = "double", desc = "A double to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the double passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the double passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String valueOfDouble(double d) {
        return String.valueOf(d);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfFloat",
        synopsis = "Returns a String representation of the float passed in.",
        signature = "String valueOfFloat (float f)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "f", type = "float", desc = "A float to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the float passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the float passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String valueOfFloat(float f) {
        return String.valueOf(f);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfChar",
        synopsis = "Returns a String representation of the char passed in.",
        enabled = @Enabled(value=false),
        signature = "String valueOfChar (char c)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "c", type = "char", desc = "A char to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the char passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the char passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String valueOfChar(char c) {
        return String.valueOf(c);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "valueOfBoolean",
        synopsis = "Returns a String representation of the boolean passed in.",
        signature = "String valueOfBoolean (boolean b)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "b", type = "boolean", desc = "A boolean to get the value of (convert to String).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the boolean passed in."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String representation of the boolean passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String valueOfBoolean(boolean b) {
        return String.valueOf(b);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "hashCode",
        synopsis = "Returns a hash code for the passed String.",
        signature = "int hashCode (String s1)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to compute a hashcode for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "A hash code for the passed String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a hash code for the passed String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int hashCode(String s1) {
        if(s1 == null) return 0;
        return s1.hashCode();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "tokenize",
        synopsis = "Breaks an input string into tokens, returning tokens in a String[].\ne.g. String.tokenize($1This is a test$1, null) returns a String[] of {$1This$1, $1is$1, $1a$1, $1test$1}.",
        signature = "String[] tokenize(String str, String delimiter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A String to be tokenized."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "delimiters", type = "String", desc = "The default is $1 $1$1$1$1t$1$1$1$1n$1$1$1$1r$1$1$1$1f$1 if null is passed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "All tokens in a String[]."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Breaks an input string into tokens, returning a string tokens in a String[].\nThe whole delimiters input string is not a single $1delimiter$1.",
        cautions = "The whole delimiters input string is not a single $1delimiter$1",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String[] tokenize(String str, String delimiters) {
        if(str == null) return null;
        StringTokenizer st = null;
        if(delimiters == null) {
            st = new StringTokenizer(str);
        }
        else {
            st = new StringTokenizer(str, delimiters);
        }
        String[] retArr = new String[st.countTokens()];
        for(int i = 0; st.hasMoreTokens(); i++) {
            retArr[i] = st.nextToken();
        }
        return retArr;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "split",
        synopsis = "Splits this string around matches of the given regular expression.\nTrailing empty strings are not included in the resulting array.\ne.g. String.split($1This|is|a|test$1, $1$1$1$1$1|$1) returns a String[] of {$1This$1, $1is$1, $1a$1, $1test$1}.",
        signature = "String[] split(String str, String regex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A String to be splitted."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "regex", type = "String", desc = "the delimiting regular expression.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "the array of strings computed by splitting this string around matches of the given regular expression."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Splits this string around matches of the given regular expression.\nTrailing empty strings are not included in the resulting array.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String[] split(String str, String regex) {
        if(str == null) return null;
        return split2(str, regex, 0);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "split2",
        synopsis = "Splits this string around matches of the given regular expression.\nThe limit parameter controls the number of times the pattern is applied and therefore\naffects the length of the resulting array. If the limit n is greater than zero then the\npattern will be applied at most n - 1 times, the array's length will be no greater than n,\nand the array's last entry will contain all input beyond the last matched delimiter.\nIf n  is non-positive then the pattern will be applied as many times as possible and the array\ncan have any length. If n is zero then the pattern will be applied as many times as possible,\nthe array can have any length, and trailing empty strings will be discarded  .\ne.g. String.split2($1This|is|a|test$1, $1$1$1$1$1|$1,4) returns a String[] of {$1This$1, $1is$1, $1a$1, $1test$1}.\ne.g. String.split2($1##$1, $1#$1) returns an empty String[] and String.split2($1##$1, $1#$1, -1) returns {$1$1,$1$1,$1$1}",
        signature = "String[] split2(String str, String regex, int limit)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A String to be splitted."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "regex", type = "String", desc = "Delimiting regular expression."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "limit", type = "int", desc = "Maximum length of the result array.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "the array of strings computed by splitting this string around matches of the given regular expression."),
        version = "3.0.2 / 4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Splits this string around matches of the given regular expression.\nThe limit parameter controls the number of times the pattern is applied and therefore\naffects the length of the resulting array.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String[] split2(String str, String regex, int limit) {
            if(str == null) return null;
            Matcher m = Pattern.compile(regex).matcher(str);
            //zero length first match in Java 7 and earlier gave an empty string as the first element of the result
            if(m.find() && m.start() == m.end()) {
            	int size = limit;
            	if(limit > 0) limit--;
            	String[] result = str.split(regex, limit);
            	if(limit <= 0) size = result.length + 1;

            	String[] newResult = new String[size];
            	System.arraycopy(result, 0, newResult, 1, size-1);
            	newResult[0] = "";
            	return newResult;
            } else {
            	return str.split(regex, limit);
            }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "trim",
        synopsis = "Returns a copy of the string, with leading and trailing whitespace omitted.",
        signature = "String trim(String str)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A String to be trimmed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "a copy of the string, with leading and trailing whitespace omitted."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a copy of the string, with leading and trailing whitespace omitted.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String trim(String str) {
        return str.trim();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "substringAfter",
        synopsis = "Returns the String after the first occurrence of the String <code>s2</code> in String <code>s1</code>",
        signature = "String substringAfter (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A String to search for in String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the substring of the first parameter string <code>s1</code> that precedes the first occurence of the second parameter string <code>s2</code>\nin the first parameter sting <code>s1</code> or null if the first parameter string <code>s1</code> does not contain the second parameter string <code>s2</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "int  result = String.substringAfter(\"This is a test. This is a test.\", \"is \");<br/><br />\n" +
                "Result is: \"is a test. This is a test.\""
    )
	public static String substringAfter(String s1, String s2) {
		if (s1==null || s2==null ||  s1.indexOf(s2) == -1) {
			return null;
		}
		else {
		    return s1.substring(s1.indexOf(s2)+s2.length(),s1.length());
		}
	}

     @com.tibco.be.model.functions.BEFunction(
        name = "substringBefore",
        synopsis = "Returns the String before the first occurrence of the String <code>s2</code> in String <code>s1</code>",
        signature = "String substringBefore (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A String to search for in String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the substring of the first parameter string <code>s1</code> that precedes the first occurence of the second parameter string <code>s2</code>\nin the first parameter sting <code>s1</code> or null if the first parameter string <code>s1</code> does not contain the second parameter string <code>s2</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String substringBefore(String s1, String s2) {
		if (s1==null || s2==null || s1.indexOf(s2) == -1) {
			return null;
		}
		else {
		   return s1.substring(0, s1.indexOf(s2));
		}
	}

   @com.tibco.be.model.functions.BEFunction(
        name = "left",
        synopsis = "Returning a String containing the first <code>length</code> characters of the string <code>s1</code>.",
        signature = "String left (String s1, int length)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "The length of the String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returning a String containing the first <code>length</code> characters of the string <code>s1</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String left(String s1, int length) {
		if (s1==null) {
			return null;
		}
		else if (s1.length() > length)
		   return s1.substring(0, length);
		else
		   return s1;
	}


	@com.tibco.be.model.functions.BEFunction(
        name = "right",
        synopsis = "Returning a String containing the last <code>length</code> characters of the string <code>s1</code>.",
        signature = "String right (String s1, int length)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "The length of the String.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returning a String containing the last <code>length</code> characters of the string <code>s1</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String right(String s1, int length) {
		if (s1 == null) {
			return null;
		}
		else if (s1.length()-length <= 0) {
			return s1;
		}
		else {
		    return s1.substring(s1.length()-length, s1.length());
		}
	}

   @com.tibco.be.model.functions.BEFunction(
        name = "contains",
        synopsis = "Tests if the String <code>s1</code> contains the String <code>s2<code>.",
        signature = "boolean contains (String s1, String s2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to test."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s2", type = "String", desc = "A String to test, if it is existing in the String <code>s1</code>.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>s1</code> contains <code>s2</code>, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Tests if the String <code>s1</code> contains String <code>s2</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static boolean contains(String s1, String s2) {
       if (s1 == null && s2 == null) {
           return true;
       }
       if (s1 == null && s2 != null) {
           return false;
       }
       if (s1 != null && s2 == null) {
           return false;
       }
		else {
			int x = s1.indexOf(s2);
			if (x>=0) {
				return true;
			}
			else {
				return false;
			}
		}
	}


	 @com.tibco.be.model.functions.BEFunction(
        name = "pad",
        synopsis = "Returns a string padded to the indicated length with the pad character.",
        signature = "String pad (String s1, int length, String padCharacter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to pad."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "Length of string."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "padCharacter", type = "String", desc = "Pad character.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The padded result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a string padded to the indicated length with the pad character.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String pad(String s1, int length, String padCharacter) {
		if (s1 == null) {
			return s1;
		}
		else if (padCharacter == null) {
			return s1;
		}
		else {
			int l = s1.length();
		    if (l >= length) {
			    return s1;
		    }
    		else
    		{
                String ret = s1;
                while(ret.length() < length) {
                    ret += padCharacter;
                }
                if(ret.length() > length) {
                    return ret.substring(0, length);
                }
                else {
                    return ret;
                }
		    }
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "padFront",
        synopsis = "Returns a string front-padded to the indicated length with the pad character.",
        signature = "String padFront(String s1, int length, String padCharacter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "A String to pad."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "length", type = "int", desc = "Length of string."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "padCharacter", type = "String", desc = "Pad character.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The front-padded result String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a string front-padded to the indicated length with the pad character.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
	public static String padFront(String s1, int length, String padCharacter) {
        if (s1 == null) {
            return s1;
        }
        else if (padCharacter == null) {
            return s1;
        }
        else {
            int l = s1.length();
            if (l >= length) {
                return s1;
            }
            else
            {
                String front = "";
                int pad_len = padCharacter.length();
                int total_len = 0 + s1.length();
                while(total_len < length) {
                    front += padCharacter;
                    total_len += pad_len;
                }
                if(total_len > length) {
                    int need_len = length - s1.length();
                    return (front.substring(0, need_len)) + s1;
                }
                else {
                    return front + s1;
                }
            }
        }
	 }

	 @com.tibco.be.model.functions.BEFunction(
        name = "convertByteArrayToString",
        synopsis = "A faster way of converting a <code>byte[]</code> to <code>String</code>\nobject.\n<p>\nThis is especially useful when the buffer sizes are very large, where\n<code>String.getBytes(String)</code> is not optimal.\n</p>",
        signature = "String convertByteArrayToString(Object bytesObject, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bytesObject", type = "Object", desc = "The input byte[]"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The character set to use")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The resulting string"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String convertByteArrayToString(Object bytesObject, String encoding) {
        return BEStringUtilities.convertByteArrayToString(bytesObject, encoding);
    }

	 @com.tibco.be.model.functions.BEFunction(
        name = "getBytes",
        synopsis = "Encodes a string to a sequence of <code>byte[]</code> using the named charset.",
        signature = "Object getBytes(String s1, String charsetName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "s1", type = "String", desc = "The input string"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "charsetName", type = "String", desc = "The charset name to use")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The resulting byte[]"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getBytes(String s1, String charsetName) {
        try {
        	if (charsetName == null) {
        		return s1.getBytes();
        	} else {
        		return s1.getBytes(charsetName);
        	}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createBuffer",
        synopsis = "Creates a new buffer to which strings can be appended instead of concat.",
        signature = "Object createBuffer(int size)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "size", type = "int", desc = "Size of buffer, this size defines the initial capacity of the buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The new buffer is created using the Java StringBuilder class.",
        cautions = "Size should not be a negative number.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object createBuffer(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Buffer size cannot be negative");
        }
        return new StringBuilder(size);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "append",
        synopsis = "Appends strings to the buffer passed to it.",
        signature = "Object append(Object stringBuffer, String stringToAppend)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stringBuffer", type = "Object", desc = "The buffer created using <b>createBuffer</b>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stringToAppend", type = "String", desc = "The string to append.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "Object createBuffer(int size)",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Appends the specified string to the character sequence.<br/>The characters of the String argument are appended, in order, increasing the length of this sequence by the length of the argument.<br/>If the string argument is <b>null</b>, then the four characters <b>\"null\"</b> are appended.<br/>Let n be the length of the character sequence prior to execution of the append method.Then the character at the index k in the new character sequence is equal to the character at the index k in the old character sequence, if k is less than n. If k is greater than n then the character at index k is equal to the character at index k-n in the argument string.",
        cautions = "Buffer parameter should not be null.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object append(Object stringBuffer, String stringToAppend) {
        if (!(stringBuffer instanceof StringBuilder)) {
            throw new IllegalArgumentException("Illegal parameter Type. Use the parameter returned by getBuffer() function");
        }
        StringBuilder stringBuilder = (StringBuilder)stringBuffer;
        stringBuilder.append(stringToAppend);
        return stringBuilder;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "convertBufferToString",
        synopsis = "Appends strings to the buffer passed to it.",
        signature = "String convertBufferToString(Object stringBuffer)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stringBuffer", type = "Object", desc = "The buffer created using <b>createBuffer</b>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "1.0",
        see = "Object createBuffer(int size)",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "Buffer parameter should not be null.",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String convertBufferToString(Object stringBuffer) {
        if (!(stringBuffer instanceof StringBuilder)) {
            throw new IllegalArgumentException("Illegal parameter Type. Use the parameter returned by getBuffer() function");
        }
        StringBuilder stringBuilder = (StringBuilder)stringBuffer;
        return stringBuilder.toString();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clearBuffer",
        synopsis = "Clears contents of the buffer passed to it.",
        signature = "void clearBuffer(Object stringBuffer)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "Object clearBuffer(Object stringBuffer)",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The method clearBuffer uses the Java StringBuilder delete method.",
        cautions = "Buffer parameter should not be null.",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void clearBuffer(Object stringBuffer) {
        if (!(stringBuffer instanceof StringBuilder)) {
            throw new IllegalArgumentException("Illegal parameter Type. Use the parameter returned by getBuffer() function");
        }
        StringBuilder stringBuilder = (StringBuilder)stringBuffer;
        stringBuilder.delete(0, stringBuilder.capacity());
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "format",
        synopsis = "Returns the message string formatted with arguments.",
        signature = "String format(String message, Object... arguments)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String", desc = "The String that contains the pattern(s) specified by %s."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arguments", type = "Object[]", desc = "The argument values to substitute..")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "with parameters substituted."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the message string formatted with arguments. Arguments are substituted in the order\nthe patterns are found.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String format(String message, Object... arguments) {
        if (message == null) {
            return null;
        }
        return String.format(message, arguments);
    }
}
