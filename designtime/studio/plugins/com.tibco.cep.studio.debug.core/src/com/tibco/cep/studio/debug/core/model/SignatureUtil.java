package com.tibco.cep.studio.debug.core.model;



public class SignatureUtil {
	
	/**
	 * Scans the given string for a type signature starting at the given index
	 * and returns the index of the last character.
	 * <pre>
	 * TypeSignature:
	 *  |  BaseTypeSignature
	 *  |  ArrayTypeSignature
	 *  |  ClassTypeSignature
	 *  |  TypeVariableSignature
	 * </pre>
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a type signature
	 */
	public static int scanTypeSignature(char[] string, int start) {
		// need a minimum 1 char
		if (start >= string.length) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		switch (c) {
			case JavaSignature.C_ARRAY :
				return scanArrayTypeSignature(string, start);
			case JavaSignature.C_RESOLVED :
			case JavaSignature.C_UNRESOLVED :
				return scanClassTypeSignature(string, start);
			case JavaSignature.C_TYPE_VARIABLE :
				return scanTypeVariableSignature(string, start);
			case JavaSignature.C_BOOLEAN :
			case JavaSignature.C_BYTE :
			case JavaSignature.C_CHAR :
			case JavaSignature.C_DOUBLE :
			case JavaSignature.C_FLOAT :
			case JavaSignature.C_INT :
			case JavaSignature.C_LONG :
			case JavaSignature.C_SHORT :
			case JavaSignature.C_VOID :
				return scanBaseTypeSignature(string, start);
			case JavaSignature.C_CAPTURE :
				return scanCaptureTypeSignature(string, start);
			case JavaSignature.C_EXTENDS:
			case JavaSignature.C_SUPER:
			case JavaSignature.C_STAR:
				return scanTypeBoundSignature(string, start);
			default :
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Scans the given string for an array type signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * ArrayTypeSignature:
	 *     <b>[</b> TypeSignature
	 * </pre>
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not an array type signature
	 */
	public static int scanArrayTypeSignature(char[] string, int start) {
		int length = string.length;
		// need a minimum 2 char
		if (start >= length - 1) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		if (c != JavaSignature.C_ARRAY) {
			throw new IllegalArgumentException();
		}
		
		c = string[++start];
		while(c == JavaSignature.C_ARRAY) {
			// need a minimum 2 char
			if (start >= length - 1) {
				throw new IllegalArgumentException();
			}
			c = string[++start];
		}
		return scanTypeSignature(string, start);
	}
	
	/**
	 * Scans the given string for a class type signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * ClassTypeSignature:
	 *     { <b>L</b> | <b>Q</b> } Identifier
	 *           { { <b>/</b> | <b>.</b> Identifier [ <b>&lt;</b> TypeArgumentSignature* <b>&gt;</b> ] }
	 *           <b>;</b>
	 * </pre>
	 * Note that although all "/"-identifiers most come before "."-identifiers,
	 * there is no syntactic ambiguity. This method will accept them without
	 * complaint.
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a class type signature
	 */
	public static int scanClassTypeSignature(char[] string, int start) {
		// need a minimum 3 chars "Lx;"
		if (start >= string.length - 2) { 
			throw new IllegalArgumentException();
		}
		// must start in "L" or "Q"
		char c = string[start];
		if (c != JavaSignature.C_RESOLVED && c != JavaSignature.C_UNRESOLVED) {
			return -1;
		}
		int p = start + 1;
		while (true) {
			if (p >= string.length) {
				throw new IllegalArgumentException();
			}
			c = string[p];
			if (c == JavaSignature.C_SEMICOLON) {
				// all done
				return p;
			} else if (c == JavaSignature.C_GENERIC_START) {
				int e = scanTypeArgumentSignatures(string, p);
				p = e;
			} else if (c == JavaSignature.C_DOT || c == '/') {
				int id = scanIdentifier(string, p + 1);
				p = id;
			}
			p++;
		}
	}

	/**
	 * Scans the given string for a type bound signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * TypeBoundSignature:
	 *     <b>[-+]</b> TypeSignature <b>;</b>
	 *     <b>*</b></b>
	 * </pre>
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a type variable signature
	 */
	public static int scanTypeBoundSignature(char[] string, int start) {
		// need a minimum 1 char for wildcard
		if (start >= string.length) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		switch (c) {
			case JavaSignature.C_STAR :
				return start;
			case JavaSignature.C_SUPER :
			case JavaSignature.C_EXTENDS :
				// need a minimum 3 chars "+[I"
				if (start >= string.length - 2) {
					throw new IllegalArgumentException();
				}
				break;
			default :
				// must start in "+/-"
					throw new IllegalArgumentException();
				
		}
		c = string[++start];
		switch (c) {
			case JavaSignature.C_CAPTURE :
				return scanCaptureTypeSignature(string, start);
			case JavaSignature.C_SUPER :
			case JavaSignature.C_EXTENDS :
				return scanTypeBoundSignature(string, start);
			case JavaSignature.C_RESOLVED :
			case JavaSignature.C_UNRESOLVED :
				return scanClassTypeSignature(string, start);
			case JavaSignature.C_TYPE_VARIABLE :
				return scanTypeVariableSignature(string, start);
			case JavaSignature.C_ARRAY :
				return scanArrayTypeSignature(string, start);
			case JavaSignature.C_STAR:
				return start;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Scans the given string for a list of type argument signatures starting at
	 * the given index and returns the index of the last character.
	 * <pre>
	 * TypeArgumentSignatures:
	 *     <b>&lt;</b> TypeArgumentSignature* <b>&gt;</b>
	 * </pre>
	 * Note that although there is supposed to be at least one type argument, there
	 * is no syntactic ambiguity if there are none. This method will accept zero
	 * type argument signatures without complaint.
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a list of type arguments
	 * signatures
	 */
	public static int scanTypeArgumentSignatures(char[] string, int start) {
		// need a minimum 2 char "<>"
		if (start >= string.length - 1) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		if (c != JavaSignature.C_GENERIC_START) {
			throw new IllegalArgumentException();
		}
		int p = start + 1;
		while (true) {
			if (p >= string.length) {
				throw new IllegalArgumentException();
			}
			c = string[p];
			if (c == JavaSignature.C_GENERIC_END) {
				return p;
			}
			int e = scanTypeArgumentSignature(string, p);
			p = e + 1;
		}
	}

	/**
	 * Scans the given string for a type argument signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * TypeArgumentSignature:
	 *     <b>&#42;</b>
	 *  |  <b>+</b> TypeSignature
	 *  |  <b>-</b> TypeSignature
	 *  |  TypeSignature
	 * </pre>
	 * Note that although base types are not allowed in type arguments, there is
	 * no syntactic ambiguity. This method will accept them without complaint.
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a type argument signature
	 */
	public static int scanTypeArgumentSignature(char[] string, int start) {
		// need a minimum 1 char
		if (start >= string.length) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		switch (c) {
			case JavaSignature.C_STAR :
				return start;
			case JavaSignature.C_EXTENDS :
			case JavaSignature.C_SUPER :
				return scanTypeBoundSignature(string, start);
			default :
				return scanTypeSignature(string, start);
		}
	}
	
	/**
	 * Scans the given string for a type variable signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * TypeVariableSignature:
	 *     <b>T</b> Identifier <b>;</b>
	 * </pre>
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a type variable signature
	 */
	public static int scanTypeVariableSignature(char[] string, int start) {
		// need a minimum 3 chars "Tx;"
		if (start >= string.length - 2) { 
			throw new IllegalArgumentException();
		}
		// must start in "T"
		char c = string[start];
		if (c != JavaSignature.C_TYPE_VARIABLE) {
			throw new IllegalArgumentException();
		}
		int id = scanIdentifier(string, start + 1);
		c = string[id + 1];
		if (c == JavaSignature.C_SEMICOLON) {
			return id + 1;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Scans the given string for an identifier starting at the given
	 * index and returns the index of the last character. 
	 * Stop characters are: ";", ":", "&lt;", "&gt;", "/", ".".
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not an identifier
	 */
	public static int scanIdentifier(char[] string, int start) {
		// need a minimum 1 char
		if (start >= string.length) { 
			throw new IllegalArgumentException();
		}
		int p = start;
		while (true) {
			char c = string[p];
			if (c == '<' || c == '>' || c == ':' || c == ';' || c == '.' || c == '/') {
				return p - 1;
			}
			p++;
			if (p == string.length) {
				return p - 1;
			}
		}
	}
	
	/**
	 * Scans the given string for a capture of a wildcard type signature starting at the given
	 * index and returns the index of the last character.
	 * <pre>
	 * CaptureTypeSignature:
	 *     <b>!</b> TypeBoundSignature
	 * </pre>
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a capture type signature
	 */
	public static int scanCaptureTypeSignature(char[] string, int start) {
		// need a minimum 2 char
		if (start >= string.length - 1) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		if (c != JavaSignature.C_CAPTURE) {
			throw new IllegalArgumentException();
		}
		return scanTypeBoundSignature(string, start + 1);
	}
	
	/**
	 * Scans the given string for a base type signature starting at the given index
	 * and returns the index of the last character.
	 * <pre>
	 * BaseTypeSignature:
	 *     <b>B</b> | <b>C</b> | <b>D</b> | <b>F</b> | <b>I</b>
	 *   | <b>J</b> | <b>S</b> | <b>V</b> | <b>Z</b>
	 * </pre>
	 * Note that although the base type "V" is only allowed in method return types,
	 * there is no syntactic ambiguity. This method will accept them anywhere
	 * without complaint.
	 * 
	 * @param string the signature string
	 * @param start the 0-based character index of the first character
	 * @return the 0-based character index of the last character
	 * @exception IllegalArgumentException if this is not a base type signature
	 */
	public static int scanBaseTypeSignature(char[] string, int start) {
		// need a minimum 1 char
		if (start >= string.length) {
			throw new IllegalArgumentException();
		}
		char c = string[start];
		if ("BCDFIJSVZ".indexOf(c) >= 0) { //$NON-NLS-1$
			return start;
		} else {
			throw new IllegalArgumentException();
		}
	}

}
