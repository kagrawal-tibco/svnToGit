package com.tibco.cep.studio.dashboard.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DisplayFormatParser {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
//	private static final Pattern MESSAGE_FORMAT_ARGUMENT_PATTERN = Pattern.compile("\\{[[\\S]&&[^\\}]]*\\}");
	
	private String displayFormat;
	
	private Map<String,String[]> argDetails; 
	
	public DisplayFormatParser(String displayFormat) {
		if (displayFormat == null) {
			throw new NullPointerException();
		}
		this.displayFormat = displayFormat;
		argDetails = new LinkedHashMap<String, String[]>();
		int startCurlyIdx = displayFormat.indexOf("{");
		while (startCurlyIdx != -1) {
			int endCurlyIdx = displayFormat.indexOf("}", startCurlyIdx);
			if (endCurlyIdx == -1) {
				break;
			}
			String formatDetails = displayFormat.substring(startCurlyIdx+1, endCurlyIdx);
			String[] argdetails = formatDetails.split(",");
			argDetails.put(argdetails[0], argdetails);
			displayFormat = displayFormat.substring(endCurlyIdx+1);
			startCurlyIdx = displayFormat.indexOf("{");
		}
//		Matcher matcher = MESSAGE_FORMAT_ARGUMENT_PATTERN.matcher(displayFormat);
//		while (matcher.find() == true){
//			StringBuilder match = new StringBuilder(matcher.group());
//			//remove {
//			match.replace(0, 1, "");
//			//remove }
//			match.replace(match.length() - 1, match.length(), "");
//			//split the match 
//			String[] argdetails = match.toString().split(",");
//			argDetails.put(argdetails[0], argdetails);
//		}		
	}
	
	public boolean isSimple(){
		int argCnt = argDetails.size();
		switch (argCnt) {
			case 0:
				//we have no arguments, check if we have text 
				return true;
			case 1:
				//check if we are character before { or after the }
				if (displayFormat.indexOf('{') != 0) {
					return false;
				}
				if (displayFormat.indexOf('}') != displayFormat.length() - 1) {
					return false;
				}
				return true;
			default:
				//we have more than one argument, so we are dealing with complex format
				return false;
		}
	}
	
	public Collection<String> getArguments(){
		return argDetails.keySet();
	}
	
	public String[] getArgumentDetails(String argument){
		return argDetails.get(argument);
	}

	public String[] getArgumentDetails() {
		Iterator<String> argumentsIterator = getArguments().iterator();
		if (argumentsIterator.hasNext() == true) {
			return getArgumentDetails(argumentsIterator.next());
		}
		return null;
	}

	public static void main(String[] args) {
		String[] tests = new String[]{
			"{ComputedRatio,number,#.00 '%'        ''}",
			"{MinRateRatio,number,#0.00 '%'}",
			"Minimum Rate ratio for {Name} is {MinRateRatio,Number,#.00}'%'",
			"{AbsoluteFlow,Number,Currency}",
			"{Name}",
			"Absolute Flow for {Name} is {AbsoluteFlow,Number,Currency}",
		};
		for (String test : tests) {
			System.out.println(test);
			DisplayFormatParser parser = new DisplayFormatParser(test);
			Collection<String> arguments = parser.getArguments();
			for (String argument : arguments) {
				String[] argumentDetails = parser.getArgumentDetails(argument);
				System.out.println("\t"+argument+"::"+Arrays.asList(argumentDetails));
			}
		}
	}
}
