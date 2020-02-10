package com.tibco.cep.studio.debug.core.launch.support;

/**
 * Used to discover the boot path, extension directories, and endorsed
 * directories for a Java VM.
 */
public class LibraryDetector {

	/**
	 * Prints system properties to standard out.
	 * <ul>
	 * <li>java.version</li>
	 * <li>sun.boot.class.path</li>
	 * <li>java.ext.dirs</li>
	 * <li>java.endorsed.dirs</li>
	 * </ul>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print(System.getProperty("java.version")); //$NON-NLS-1$
		System.out.print("|"); //$NON-NLS-1$
		System.out.print(System.getProperty("sun.boot.class.path")); //$NON-NLS-1$
		System.out.print("|"); //$NON-NLS-1$
		System.out.print(System.getProperty("java.ext.dirs")); //$NON-NLS-1$
		System.out.print("|"); //$NON-NLS-1$
		System.out.print(System.getProperty("java.endorsed.dirs")); //$NON-NLS-1$
	}
}