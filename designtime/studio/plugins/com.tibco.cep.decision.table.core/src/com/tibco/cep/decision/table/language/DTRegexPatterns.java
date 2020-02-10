package com.tibco.cep.decision.table.language;

import java.util.regex.Pattern;

/**
 * Class containing regex pattern constants used in DTs
 *
 */
public class DTRegexPatterns {
	
	/**
	 * Cell Value pattern e.g : [1:2:Number.intValue("10",10)]abc , or abc
	 */
	public static final Pattern ARRAY_INDEX_PATTERN = Pattern.compile("(.*)(\\[[\\.\\(\\)[\\s]\"[+*-]@,%a-zA-Z0-9]*(:([\\.\\(\\)[\\s]\"[+*-]@,%a-zA-Z0-9])*)*\\])*(.*)");
	
	/**
	 * Standard operator pattern
	 */
	public static final Pattern ANY_OPERATOR_PATTERN = Pattern.compile("[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)");
	
	/**
	 * AND (&&) and OR (||) operator pattern. Either one is matched.
	 */
	public static final Pattern AND_OR_PATTERN = Pattern.compile("[\\|\\|\\&\\&][\\|\\|\\&\\&]");
	
	/**
	 * Array style name pattern e:g a.b[].c[].....d[].prop 
	 */
	public static final Pattern ARRAY_STYLE_PATTERN = Pattern.compile("(.*)\\[\\](.*)");
	
	/**
	 * Substitution pattern for condition columns e.g: a.b >= {0}
	 */
	public static final Pattern COND_SUBSTITUTION_PATTERN = Pattern.compile("(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)\\{(.*)\\}(.*)");
	
	/**
	 * Substitution pattern for condition columns e.g: {0} > a.b
	 */
	public static final Pattern COND_SUBSTITUTION_PATTERN_BEFORE = Pattern.compile("(.*)\\{(.*)\\}(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)");
	
	/**
	 * Substitution pattern for action columns e.g: a.b = {0}
	 */
	public static final Pattern ACT_SUBSTITUTION_ASSIGNMENT_PATTERN = Pattern.compile("(.*)[^><]=(.*)\\{(.*)\\}(.*)");
	
	/**
	 * Substitution pattern for action columns e.g: {0} = a.b e.g:(System.getGlobalVariableAsInt(%%gv%%) = concept.intProp)
	 */
	public static final Pattern ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE = Pattern.compile("(.*)\\{(.*)\\}(.*)[^><]=(.*)");
	
	/**
	 * Equality pattern for action substitution column. Should not be matched in actions.
	 */
	public static final Pattern ACT_SUBSTITUTION_EQUALITY_PATTERN = Pattern.compile("(.*)[^><]==(.*)\\{(.*)\\}(.*)");
	
	/**
	 * Equality pattern for action substitution column. Should not be matched in actions.
	 */
	public static final Pattern ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE = Pattern.compile("(.*)\\{(.*)\\}(.*)[^><]==(.*)");
	
	/**
	 * Pattern to check whether string ends with ;
	 */
	public static final Pattern ENDS_WITH_SEMICOLON = Pattern.compile(";\\s*$");

	public static final Pattern STAR = Pattern.compile("^\\s*\\*\\s*$");
	
	/**
	 * Pattern to check if string ends with say loop<n>
	 */
	public static final Pattern ENDS_WITH_ARRAY = Pattern.compile("(.*)\\[\\]\\s*$");
	
	/**
	 * Pattern to check for return statement
	 */
	public static final Pattern RETURN_REGEX = Pattern.compile("return[\\W\\n]");
	
	/**
	 * Standard datetime function match
	 */
	public static final Pattern DATETIME_FUNC_PATTERN = Pattern.compile("DateTime(.*)");
	
	/**
	 * Match [0:1], or [abc], or [Integer.parseInt("10")] etc.
	 */
	public static final Pattern ARRAY_BRACKETS_PATTERN = Pattern.compile("\\[(.*)\\]");
	
	/**
	 * Standard datetime String match (Should begin with whitespace or datetime literal only)
	 * <p>
	 * Very difficult to do if-else here. e.g : 24 is correct but 29 is not for hours field.
	 * </p>
	 */
	public static final Pattern DATETIME_LITERAL_PATTERN = Pattern.compile("^((\\p{javaWhitespace})*(\\[(.*)\\])*(\\p{javaWhitespace})*([0-9]{4}-[0-1]{1}[0-9]{1}-[0-9]{2}T[0-2]{1}[0-9]{1}:[0-6]{1}[0-9]{1}:[0-6]{1}[0-9]{1}))");
	
	/**
	 * Null literal pattern. Can contain leading and trailing whitespaces. e.g : "  null "
	 */
	public static Pattern NULL_LITERAL_PATTERN = Pattern.compile("\\p{javaWhitespace}*null\\p{javaWhitespace}*");
	
	/**
	 * Match function call e.g : System.debugOut("Hello");
	 */
	public static final Pattern A_FUNCTION_PATTERN = Pattern.compile("(.*)\\.(.*)\\(+(.*)\\p{javaWhitespace}*\\)+");
	
	/*
	 * Cusotm header pattern
	 */
	public static final Pattern CUSTOM_HEADER_PATTERN = Pattern.compile("Custom\\s+(Condition|Action)\\s+(\\d)+");
}
