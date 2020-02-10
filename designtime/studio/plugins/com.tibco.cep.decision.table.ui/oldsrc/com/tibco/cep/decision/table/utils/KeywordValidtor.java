/**
 * 
 */
package com.tibco.cep.decision.table.utils;


/**
 * @author aathalye
 *
 */
public class KeywordValidtor {
	
	private final static String[] KEYWORDS = 
	{"new", "if", "for", "else", "class", "public",
		"private", "implements", "import", "throw",
		"throws", "boolean", "int", "float", "double",
		"package", "null", "true", "false", "static",
		"final", "catch", "finally", "try", "void", "assert",
		"return", "protected", "abstract", "long", "byte", "case",
		"transient", "native", "short", "volatile", "strictfp"
	};
	
	public static boolean validate(String input) {
		if (input == null || input.length() == 0) {
			return true;
		}
		for (int i = 0; i < KEYWORDS.length; i++) {
			if (KEYWORDS[i] == input.intern()) {
				return false;
			}
		}
		return true;
	}
}
