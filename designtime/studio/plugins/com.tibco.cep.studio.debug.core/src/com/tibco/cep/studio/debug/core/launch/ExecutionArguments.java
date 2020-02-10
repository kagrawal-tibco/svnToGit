package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.debug.core.DebugPlugin;

/**
 * The execution arguments for running a Java VM. The execution arguments are
 * separated into two parts: arguments to the VM itself, and arguments to the Java 
 * main program. This class provides convenience methods for parsing a string
 * of arguments into separate components.
 * <p>
 * Clients may instantiate this class.
 * </p>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ExecutionArguments {
	private String fVMArgs;
	private String fProgramArgs;
		
	/**
	 * Creates a new execution arguments object.
	 *
	 * @param vmArgs command line argument string passed to the VM
	 * @param programArgs command line argument string passed to the program
	 */
	public ExecutionArguments(String vmArgs, String programArgs) {
		if (vmArgs == null || programArgs == null)
			throw new IllegalArgumentException();
		fVMArgs= vmArgs;
		fProgramArgs= programArgs;
	}
	
	/**
	 * Returns the VM arguments as one string.
	 *
	 * @return the VM arguments as one string
	 */
	public String getVMArguments() {
		return fVMArgs;
	}
	
	/**
	 * Returns the program arguments as one string.
	 *
	 * @return the program arguments as one string
	 */
	public String getProgramArguments() {
		return fProgramArgs;
	}
	
	/**
	 * Returns the VM arguments as an array of individual arguments.
	 *
	 * @return the VM arguments as an array of individual arguments
	 */
	public String[] getVMArgumentsArray() {
		return DebugPlugin.parseArguments(fVMArgs);
	}
	
	/**
	 * Returns the program arguments as an array of individual arguments.
	 *
	 * @return the program arguments as an array of individual arguments
	 */
	public String[] getProgramArgumentsArray() {
		return DebugPlugin.parseArguments(fProgramArgs);
	}	
			
}
