package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VariableParser {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	private static final String PATTERN0 = "(\\$\\{([a-zA-Z]+):([^}]+)\\})";
	
	private static VariableParser instance;

	public static final synchronized VariableParser getInstance() {
		if (instance == null) {
			instance = new VariableParser();
		}
		return instance;
	}
	
	private Pattern variablePattern; 
	
	private VariableProvider defaultVariableProvider = new CONSTANTVariableProvider();
	
	private List<VariableProvider> variableProviders;

	private VariableParser() {
		variablePattern = Pattern.compile(PATTERN0, Pattern.CASE_INSENSITIVE);
		variableProviders = new LinkedList<VariableProvider>();
		variableProviders.add(new ENVVariableProvider());
		variableProviders.add(new PROPVariableProvider());
		variableProviders.add(new SYSVariableProvider());
		variableProviders.add(new USRVariableProvider());
		variableProviders.add(new VARVariableProvider());
		variableProviders.add(new GVARVariableProvider());
	}
	
	public Collection<VariableProvider> getVariableProviders() {
		return Collections.unmodifiableCollection(variableProviders);
	}
	
	public VariableProvider getVariableProvider(String identifier) {
		for (VariableProvider variableProvider : variableProviders) {
			if (variableProvider.getIdentifier().equals(identifier) == true) {
				return variableProvider;
			}
		}
		return null;
	}
	
	public VariableExpression parse(String expression) throws ParseException{
		if (expression == null) {
			return null;
		}
		VariableExpression variableExpression = new VariableExpression(expression);
		StringBuilder parsedExpression = new StringBuilder();
		List<Variable> variables = parse(expression, parsedExpression);
		variableExpression.setParsedExpression(parsedExpression.toString());
		variableExpression.setVariables(variables);
		return variableExpression;
	}
	
	private List<Variable> parse(String expression, StringBuilder parsedExpression) throws ParseException {
		List<Variable> variables = new LinkedList<Variable>();
		Matcher urlMatcher = variablePattern.matcher(expression);
		int paramCtr = 0;
		int startIdx = 0;
		while (urlMatcher.find()) {
			parsedExpression.append(expression.substring(startIdx, urlMatcher.start()));
			for (int i = 0; i <= urlMatcher.groupCount(); i++) {
				switch (i) {
				case 0: // This is match=${subsRefType:subsRefKey}
					break;
				case 1: // This is subsRef=${subsRefType:subsRefKey}
					parsedExpression.append("{" + paramCtr++ + "}");
					variables.add(parseVariable(urlMatcher.group(i)));
					break;
				case 2: // This is subsRefType (could be used directly)
				case 3: // This is subsRefKey (could be used directly)
					break;
				default:
					throw new ParseException("Unexpected match! " + urlMatcher.group(i),-1);
				}
			}
			startIdx = urlMatcher.end();
		}
		parsedExpression.append(expression.substring(startIdx, expression.length()));
		return variables;		
	}

	private Variable parseVariable(String variableExpression) throws ParseException {
		if (variableExpression != null && variableExpression.startsWith("${") == true && variableExpression.endsWith("}") == true) {
			variableExpression = variableExpression.substring(2,variableExpression.length()-1);
			String[] typeAndArg = variableExpression.split(":");
			if (typeAndArg.length == 2) {
				String identifier = typeAndArg[0];
				String argument = typeAndArg[1];
				Variable variable = null;
				for (VariableProvider variableProvider : variableProviders) {
					if (variableProvider.getIdentifier().equalsIgnoreCase(identifier) == true) {
						variable = variableProvider.parse(argument);
					}
				}
				if (variable == null) {
					throw new ParseException("Unknown variable type ["+identifier+"]",-1);
				}
				return variable;
			}
			throw new ParseException("Invalid variable expression ["+variableExpression+"]",-1);
		}
		return defaultVariableProvider.parse(variableExpression);
	}

	
	public static void main(String[] args) {
		String[] testcases = new String[] {
//			null,
//			"",
//			"           ",
//			"$",
//			"$$",
//			"${",
//			"}",
//			"s432423423adsa",
//			"${}",
//			"${FDSfsdfsfsdfsd}",
//			"sadasdasdsa ${}sdasdasdasd",
//			"32132131 ${FDSfsdfsfsdfsd} 123123131",
//			"${ENV:java.home}",
//			"Foo ${ENV:java.home} Bar",
//			"${ENV::java.home}",
//			"${CONSTANT:java.home}",
			"${PROP:java.home}",
			"${SYS:java.home}",
			"${SYS:STARTOFYESTERDAY}",
			"${SYS:ENDOFYESTERDAY}",
			"${SYS:YESTERDAY}",
			"${SYS:STARTOFTODAY}",
			"${SYS:ENDOFTODAY}",
			"${SYS:TODAY}",
			"${SYS:NOW}",
			"${USR:NAME}",
			"${USR:ROLE}",
			"${gvar:foo}",
			"${GVAR:ENDOFYESTERDAY}",
		};
		
		StringBuilder sb = new StringBuilder();
		for (String testcase : testcases) {
			try {
				VariableExpression expression = VariableParser.getInstance().parse(testcase);
				System.out.println(testcase+" :: "+expression);
				sb.append(testcase);
				sb.append(" ");
			} catch (ParseException e) {
				System.out.println(testcase+" :: "+e.getMessage());
			}
		}
		try {
			VariableExpression expression = VariableParser.getInstance().parse(sb.toString());
			System.out.println(sb+" :: "+expression);
		} catch (ParseException e) {
			System.out.println(sb+" :: "+e.getMessage());
		}
	}
}
