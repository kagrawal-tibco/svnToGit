//
//	ParameterReader.java
//
//	Copyright:
//		Tom Sawyer Software, 1992 - 2005
//		1625 Clay Street
//		Sixth Floor
//		Oakland, CA 94612
//		U.S.A.
//
//		Web: www.tomsawyer.com
//
//		All Rights Reserved.
//

package com.tibco.cep.diagramming.tool.popup;


/**
 * This interface defines a simple parameter reader that returns a
 * parameter requested with a given key. Applications that implement
 * this interface should parse their command line arguments;
 * applets implement the reader by default.
 */
public interface ParameterReader
{
	/**
	 * This method returns the requested parameter.
	 * @param key the key to which the requested parameter is mapped.
	 * @return the requested parameter, or <code>null</code> if it
	 * was not found.
	 */
	public String getParameter(String key);
}
