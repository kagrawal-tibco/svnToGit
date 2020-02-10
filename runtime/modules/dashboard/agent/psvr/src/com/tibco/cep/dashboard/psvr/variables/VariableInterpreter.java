package com.tibco.cep.dashboard.psvr.variables;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.dashboard.core.variables.Variable;
import com.tibco.cep.studio.dashboard.core.variables.VariableExpression;
import com.tibco.cep.studio.dashboard.core.variables.VariableParser;

public class VariableInterpreter {
	
	private static final String PATTERN0 = "(\\{([\\d]+)\\})";
	
	private static VariableInterpreter instance;

	public static final synchronized VariableInterpreter getInstance() {
		if (instance == null) {
			instance = new VariableInterpreter();
		}
		return instance;
	}
	
	private Pattern variablePattern;

	private VariableValueProvider defaultVariableProvider = new CONSTANTVariableValueProvider();
	
	private List<VariableValueProvider> variableProviders;

	private VariableInterpreter() {
		variablePattern = Pattern.compile(PATTERN0, Pattern.CASE_INSENSITIVE);
		variableProviders = new LinkedList<VariableValueProvider>();
		variableProviders.add(new ENVVariableValueProvider());
		variableProviders.add(new PROPVariableValueProvider());
		variableProviders.add(new SYSVariableValueProvider());
		variableProviders.add(new USRVariableValueProvider());
		variableProviders.add(new VARVariableValueProvider());
		variableProviders.add(new GVARVariableValueProvider());
	}

	public String interpret(String expression, VariableContext context, boolean encode) throws ParseException {
		//parse the expression 
		return interpret(VariableParser.getInstance().parse(expression), context, encode);
	}
	
	public String interpret(VariableExpression variableExpression, VariableContext context, boolean encode) {
		if (variableExpression == null) {
			return "";
		}
		String parsedExpression = variableExpression.getParsedExpression();
		Matcher urlMatcher = variablePattern.matcher(parsedExpression);
		StringBuilder sb = new StringBuilder();
		int paramCnt = 0;
		int startIdx = 0;
		while (urlMatcher.find()) {
			sb.append(parsedExpression.substring(startIdx, urlMatcher.start()));
			String value = getValue(context,variableExpression.getVariables().get(paramCnt++));
			if (encode == true) {
				try {
					value = URLEncoder.encode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
			}
			sb.append(value);
			startIdx = urlMatcher.end();
		}
		sb.append(parsedExpression.substring(startIdx, parsedExpression.length()));
		return sb.toString();
	}


	private String getValue(VariableContext context, Variable variable) {
		String identifier = variable.getIdentifier();
		for (VariableValueProvider variableValueProvider : variableProviders) {
			if (identifier.equals(variableValueProvider.getIdentifier()) == true) {
				return variableValueProvider.getValue(context, variable);
			}
		}
		return defaultVariableProvider.getValue(context, variable);
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
				String result = VariableInterpreter.getInstance().interpret(testcase, new VariableContext(new SysOutLogger(), null, null, null), false);
				System.out.println(testcase+" :: "+result);
				sb.append(testcase);
				sb.append(" ");
			} catch (ParseException e) {
				System.out.println(testcase+" :: "+e.getMessage());
			}
		}
		try {
			String result = VariableInterpreter.getInstance().interpret(sb.toString(), new VariableContext(new SysOutLogger(), null, null, null), false);
			System.out.println(sb+" :: "+result);
		} catch (ParseException e) {
			System.out.println(sb+" :: "+e.getMessage());
		}
	}	
	
	static class SysOutLogger implements Logger {

		@Override
		public void close() {
		}

		@Override
		public Level getLevel() {
			return Level.TRACE;
		}

		@Override
		public String getName() {
			return "sysout";
		}

		@Override
		public boolean isEnabledFor(Level level) {
			return true;
		}

		@Override
		public void log(Level level, String msg) {
			System.out.println(level+"::"+msg);			
		}

		@Override
		public void log(Level level, String format, Object... args) {
			System.out.println(level+"::"+String.format(format,args));
		}

		@Override
		public void log(Level level, String format, Throwable thrown, Object... args) {
			System.out.println(level+"::"+String.format(format,args));
			thrown.printStackTrace();
		}

		@Override
		public void log(Level level, Throwable thrown, String msg) {
			System.out.println(level+"::"+msg);
			thrown.printStackTrace();
		}

		@Override
		public void log(Level level, Throwable thrown, String format, Object... args) {
			System.out.println(level+"::"+String.format(format,args));
			thrown.printStackTrace();			
		}

		@Override
		public void setLevel(Level level) {
						
		}
		
	}

}