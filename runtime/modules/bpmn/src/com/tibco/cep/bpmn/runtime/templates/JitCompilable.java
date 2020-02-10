package com.tibco.cep.bpmn.runtime.templates;

import java.io.ByteArrayOutputStream;

/**
 * @author pdhar
 *
 */
public class JitCompilable {
	
	private ByteArrayOutputStream codeStream;
	private String packageName;	
	private String className;
	
	public JitCompilable(ByteArrayOutputStream codeStream, String packageName, String className) {
		super();
		this.codeStream = codeStream;
		this.packageName = packageName;
		this.className = className;
	}

	/**
	 * @return the codeStream
	 */
	public ByteArrayOutputStream getCodeStream() {
		return codeStream;
	}

	/**
	 * @param codeStream the codeStream to set
	 */
	public void setCodeStream(ByteArrayOutputStream codeStream) {
		this.codeStream = codeStream;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	

}
