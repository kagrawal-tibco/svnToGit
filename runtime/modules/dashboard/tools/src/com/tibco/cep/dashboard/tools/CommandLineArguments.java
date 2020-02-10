package com.tibco.cep.dashboard.tools;

import java.util.Stack;

public class CommandLineArguments {
	
	private Stack<String> arguments;
	
	public CommandLineArguments(String[] args){
		arguments = new Stack<String>();
		for (int i = args.length - 1; i >= 0; i--) {
			arguments.push(args[i]);
		}
	}

	public String peek() {
		if (arguments.isEmpty() == true){
			return "";
		}
		return arguments.peek();
	}

	public String pop() {
		if (arguments.isEmpty() == true){
			return "";
		}		
		return arguments.pop();
	}

	public int size() {
		return arguments.size();
	}
	
}