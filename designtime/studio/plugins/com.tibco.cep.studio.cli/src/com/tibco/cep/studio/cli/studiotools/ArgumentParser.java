package com.tibco.cep.studio.cli.studiotools;

import java.util.LinkedHashMap;
import java.util.Map;

/*
@author ssailapp
@date Aug 21, 2009 5:17:30 PM
 */

public class ArgumentParser {

	private Map<String, String> argsMap;
	private int argsLength;
	
	public ArgumentParser() {
		argsMap = new LinkedHashMap<String, String>();
	}

	// Parser to support spaces (revert to the old one below, if needed)	
	public boolean parseArguments(String[] args) {
		if (args.length==1 && !args[0].trim().startsWith("-"))
			return false;
		for (int i=0; i<args.length;) {
			String option = args[i].trim();
			if (!option.startsWith("-")) {
				i++;
				continue;		// this is a value
			}
            final StringBuffer value = new StringBuffer();
            for (i++; (i<args.length) && !args[i].startsWith("-"); i++) {
                value.append(" ").append(args[i]);
            }
            this.argsMap.put(option, value.toString().trim());
		}
		argsLength = args.length;
		return true;
	}
	
	// This is the old parser, which works well but doesn't support spaces
	public boolean parseArgumentsOld(String[] args) {
		for (int i=0; i<args.length; i++) {
			String option = args[i].trim();
			if (!option.startsWith("-"))
				continue;		// this is a value
			if (i+1<args.length) {
				String value = args[i+1].trim();
				if (!option.equals("")) {
					if (!value.equals("") && !value.startsWith("-")) {
						argsMap.put(option, value);
						continue;
					}
				}
			}
			argsMap.put(option, null);
		}
		argsLength = args.length;
		return true;
	}
	
	public Map<String, String> getArgsMap() {
		return argsMap;
	}
	
	public String getHelpOperation() {
		return (argsMap.get(HelpCLI.OPERATION_HELP));
	}
	
	public int getArgsLength() {
		return argsLength;
	}
}
