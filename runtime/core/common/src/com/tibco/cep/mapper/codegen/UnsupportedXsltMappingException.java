package com.tibco.cep.mapper.codegen;

/**
 * Exception that is thrown if java cannot be directly
 * generated from the XSLT mapping
 * @author rhollom
 *
 */
public class UnsupportedXsltMappingException extends
		UnsupportedOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedXsltMappingException(String message) {
		super(message);
	}

}
