package com.tibco.cep.studio.cli.studiotools;

import java.io.IOException;

/**
 * @author pdhar
 * 
 */
public class ConsoleInput {
	private static final String CONSOLE = ">"; //$NON-NLS-1$
	private static final String DEFAULT_PROMPT = "Enter Input: "; //$NON-NLS-1$
	private static final String DEFAULT_RETRY = "Invalid Input. Retry. "; //$NON-NLS-1$

	/**
	 * @param prompt
	 * @return
	 */
	public static final boolean readYesNo(String prompt,boolean console) {
		String input = readLine(prompt, console).toLowerCase().trim();
		if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
			return true;
		} 
		if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
			return false;
		} 
		if(!input.toLowerCase().startsWith("y")&& !input.toLowerCase().startsWith("n")){
			System.out.println("\n");
			return readYesNo(prompt,console);
		}
		return false;
	}

	/**
	 * @param prompt
	 * @param console TODO
	 * @return
	 */
	private static String readLine(String prompt, boolean console) {
		System.out.print(prompt);
		if(console) {
			System.out.println("\n");
			System.out.println(CONSOLE);
		}
		StringBuilder sb = new StringBuilder();
		while (true) {
			try {
				char c = (char) System.in.read();
				sb.append(c);
				if (c == '\n') {
					return sb.toString().trim();
				} else if (c == '\r') {
				} // ignore cr
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	public static final boolean readYesNo() {
		return readYesNo(DEFAULT_PROMPT,true);
	}

	/**
	 * @param prompt
	 * @return
	 */
	public static final boolean readBoolean(String prompt) {
		return readYesNo(prompt,true);
	}

	/**
	 * @return
	 */
	public static final boolean readBoolean() {
		return readYesNo(DEFAULT_PROMPT,true);
	}

	/**
	 * @param prompt
	 * @param error
	 * @param min
	 * @param max
	 * @return
	 */
	public static final int readInteger(String prompt, String error, int min, int max) {
		if (min > max)
			return 0;
		try {
			int i = Integer.valueOf(readLine(prompt, false));
			if ((i < min) || (i > max)) {
				System.out.println(error);
				return readInteger(prompt, error, min, max);
			}
			return i;

		} catch (NumberFormatException e) {
			System.out.println(error);
			return readInteger(prompt, error, min, max);
		}
	}

	/**
	 * @param prompt
	 * @param min
	 * @param max
	 * @return
	 */
	public static final int readInteger(String prompt, int min, int max) {
		return readInteger(prompt, DEFAULT_RETRY, min, max);
	}

	/**
	 * @param min
	 * @param max
	 * @return
	 */
	public static final int readInteger(int min, int max) {
		return readInteger(DEFAULT_PROMPT, DEFAULT_RETRY, min, max);
	}

	/**
	 * @param prompt
	 * @param error
	 * @return
	 */
	public static final int readInteger(String prompt, String error) {
		try {
			return Integer.valueOf((readLine(prompt, false)));
		} catch (NumberFormatException e) {
			System.out.println("\n" + error);
			return readInteger(prompt, error);
		}
	}

	/**
	 * @param prompt
	 * @return
	 */
	public static final int readInteger(String prompt) {
		return readInteger(prompt, DEFAULT_RETRY);
	}

	/**
	 * @return
	 */
	public static final int readInteger() {
		return readInteger(DEFAULT_PROMPT, DEFAULT_RETRY);
	}

	/**
	 * @param prompt
	 * @param error
	 * @return
	 */
	public static final double readDouble(String prompt, String error) {
		try {
			return Double.valueOf((readLine(prompt, false)));
		} catch (NumberFormatException e) {
			System.out.println("\n" + error);
			return readDouble(prompt, error);
		}
	}

	/**
	 * @param prompt
	 * @return
	 */
	public static final double readDouble(String prompt) {
		return readDouble(prompt, DEFAULT_RETRY);
	}

	/**
	 * @return
	 */
	public static final double readDouble() {
		return readDouble(DEFAULT_PROMPT, DEFAULT_RETRY);
	}

	/**
	 * @param prompt
	 * @param error
	 * @return
	 */
	public static final float readFloat(String prompt, String error) {
		try {
			return Float.valueOf((readLine(prompt, false)));
		} catch (NumberFormatException e) {
			System.out.println("\n" + error);
			return readFloat(prompt, error);
		}
	}

	/**
	 * @param prompt
	 * @return
	 */
	public static final float readFloat(String prompt) {
		return readFloat(prompt, DEFAULT_RETRY);
	}

	/**
	 * @return
	 */
	public static final float readFloat() {
		return readFloat(DEFAULT_PROMPT, DEFAULT_RETRY);
	}

	/**
	 * @param prompt
	 * @param error
	 * @param minLength
	 * @return
	 */
	public static final String readString(String prompt, String error, int minLength) {
		String input = readLine(prompt, false);
		if (input.length() < minLength) {
			System.out.println("\n" + error);
			return readString(prompt, error, minLength);
		}

		return input;
	}

	/**
	 * @param prompt
	 * @param error
	 * @return
	 */
	public static final String readString(String prompt, String error) {
		String input = readLine(prompt, false);
		if ((input.length() == 0) || (input == null) || (input.equals("\n"))) {
			System.out.println("\n" + error);
		}

		return input;
	}

	/**
	 * @param prompt
	 * @return
	 */
	public static final String readString(String prompt) {
		return readString(prompt, DEFAULT_RETRY);
	}

	/**
	 * @return
	 */
	public static final String readString() {
		return readString(DEFAULT_PROMPT, DEFAULT_RETRY);
	}

}
